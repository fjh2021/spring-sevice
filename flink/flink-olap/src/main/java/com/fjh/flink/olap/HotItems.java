package com.fjh.flink.olap;

import com.fjh.flink.olap.bean.ItemViewCount;
import com.fjh.flink.olap.bean.UserBehavior;
import org.apache.commons.compress.utils.Lists;
import org.apache.flink.api.common.eventtime.WatermarkStrategy;
import org.apache.flink.api.common.functions.AggregateFunction;
import org.apache.flink.api.common.serialization.SimpleStringSchema;
import org.apache.flink.api.common.state.ListState;
import org.apache.flink.api.common.state.ListStateDescriptor;
import org.apache.flink.configuration.Configuration;
import org.apache.flink.connector.jdbc.JdbcConnectionOptions;
import org.apache.flink.connector.jdbc.JdbcExecutionOptions;
import org.apache.flink.connector.jdbc.JdbcSink;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.api.functions.KeyedProcessFunction;
import org.apache.flink.streaming.api.functions.windowing.WindowFunction;
import org.apache.flink.streaming.api.windowing.assigners.SlidingEventTimeWindows;
import org.apache.flink.streaming.api.windowing.time.Time;
import org.apache.flink.streaming.api.windowing.windows.TimeWindow;
import org.apache.flink.streaming.connectors.kafka.FlinkKafkaConsumer;
import org.apache.flink.util.Collector;

import java.time.Duration;
import java.util.Comparator;
import java.util.List;
import java.util.Properties;

/**
 * <p>
 *
 * </p>
 *
 * @author fjh
 * @since 2021/11/28 14:27
 */
public class HotItems {

    public static void main(String[] args) throws Exception {
        //创建环境
        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
        env.setParallelism(1);
        //读取数据
//        DataStream<String> inputSteam = env.readTextFile("D:\\");
        // kafka 数据读取
        Properties properties = new Properties();
        properties.setProperty("bootstrap.servers", "hg-dev:9092");
        properties.setProperty("group.id", "consumer");
        properties.setProperty("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        properties.setProperty("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        properties.setProperty("auto.offset.reset", "latest");
        DataStream<String> inputSteam = env.addSource(new FlinkKafkaConsumer<String>("hotitems", new SimpleStringSchema(), properties));
        //转换成pojo 和 设置watermark
        DataStream<UserBehavior> dataStream = inputSteam.map(line -> {
            String[] fields = line.split(",");
            return new UserBehavior(new Long(fields[0]), new Long(fields[1]), new Integer(fields[2]), fields[3], new Long(fields[4]));
        }).assignTimestampsAndWatermarks(WatermarkStrategy.<UserBehavior>forBoundedOutOfOrderness(Duration.ofSeconds(3))
                .withTimestampAssigner((event, timestamp) -> event.getTimestamp()*1000));

        //过滤，分组开窗聚合
        DataStream<ItemViewCount> itemViewCountDataStream = dataStream.filter(data ->
                        "pv".equals(data.getBehavior()))  //过滤pv
                .keyBy(x -> x.getItemId())   //分组
                .window(SlidingEventTimeWindows.of(Time.hours(1L), Time.minutes(4))) //滑动窗口
                .aggregate(new ItemCountAgg(), new WindowItemCountResult());

        //同一窗口的商品排序，top5
        DataStream<String> resultSteam = itemViewCountDataStream
                .keyBy(ItemViewCount::getWindowEnd)
                .process(new TopHotItem(5));
        resultSteam.print();

        itemViewCountDataStream
                .addSink(JdbcSink.sink(
                        "insert into flink_hot_item(item_id,item_count,window_end) values (?,?,?)",
                        (ps, t) -> {
                            ps.setLong(1, t.getItemId());
                            ps.setLong(2, t.getCount());
                            ps.setLong(3, t.getWindowEnd());
                        },
                        new JdbcExecutionOptions.Builder().withBatchSize(1).build(),
                        new JdbcConnectionOptions.JdbcConnectionOptionsBuilder()
                                .withUrl("jdbc:mysql://119.91.201.111:3306/flinkdb?useSSL=false&autoReconnect=true&useUnicode=true&characterEncoding=utf8&serverTimezone=Asia/Shanghai")
                                .withDriverName("com.mysql.cj.jdbc.Driver")
                                .withUsername("dev")
                                .withPassword("dev123456.")
                                .build()));
        env.execute();
    }

    //实现自定义增量聚合函数(预聚合）
    public static class ItemCountAgg implements AggregateFunction<UserBehavior, Long, Long> {


        @Override
        public Long createAccumulator() {
            return 0L;
        }

        @Override
        public Long add(UserBehavior userBehavior, Long o) {

            return o + 1;
        }

        @Override
        public Long getResult(Long o) {
            return o;
        }

        @Override
        public Long merge(Long a, Long b) {

            return a + b;
        }
    }

    //结果全窗口函数
    public static class WindowItemCountResult implements WindowFunction<Long, ItemViewCount, Long, TimeWindow> {

        @Override
        public void apply(Long aLong, TimeWindow window, Iterable<Long> input, Collector<ItemViewCount> out) throws Exception {
            Long itemId = aLong;
            Long windowEnd = window.getEnd();
            Long count = input.iterator().next();
            out.collect(new ItemViewCount(itemId, windowEnd, count));
        }
    }

    public static class TopHotItem extends KeyedProcessFunction<Long, ItemViewCount, String> {

        private Integer topSize;

        TopHotItem(Integer topSize) {
            this.topSize = topSize;
        }

        // 定义列表状态，保存当前输出ItemViewCount
        ListState<ItemViewCount> itemViewCountListState;

        @Override
        public void open(Configuration parameters) throws Exception {
//            super.open(parameters);
            itemViewCountListState = getRuntimeContext().getListState(new ListStateDescriptor<ItemViewCount>("itemViewCountList", ItemViewCount.class));
        }

        @Override
        public void processElement(ItemViewCount value, KeyedProcessFunction<Long, ItemViewCount, String>.Context ctx, Collector<String> out) throws Exception {
            itemViewCountListState.add(value);
            ctx.timerService().registerEventTimeTimer(value.getWindowEnd() + 1);
        }


        @Override
        public void onTimer(long timestamp, KeyedProcessFunction<Long, ItemViewCount, String>.OnTimerContext ctx, Collector<String> out) throws Exception {
//            super.onTimer(timestamp, ctx, out);
            List<ItemViewCount> itemViewCountList = Lists.newArrayList(itemViewCountListState.get().iterator());
            itemViewCountList.sort(new Comparator<ItemViewCount>() {
                @Override
                public int compare(ItemViewCount o1, ItemViewCount o2) {
                    return o2.getCount().intValue() - o1.getCount().intValue();
                }
            });

            //打印排名
            StringBuffer stringBuffer = new StringBuffer();
            for (int i = 0; i < Math.min(topSize, itemViewCountList.size()); i++) {
                ItemViewCount itemViewCount = itemViewCountList.get(i);
                stringBuffer.append("top:").append(i + 1).append("\n");
                stringBuffer.append("商品:").append(itemViewCount.getItemId()).append("\n");
                stringBuffer.append("商品总数:").append(itemViewCount.getCount()).append("\n");
                stringBuffer.append("窗口:").append(itemViewCount.getWindowEnd()).append("\n");
                stringBuffer.append("--------------").append("\n");
            }
            stringBuffer.append("----------------------------------------------------------").append("\n");
            out.collect(stringBuffer.toString());
        }
    }


}
