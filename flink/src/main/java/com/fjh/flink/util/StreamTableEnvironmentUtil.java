package com.fjh.flink.util;

import com.fjh.flink.param.JobParam;
import org.apache.flink.api.common.restartstrategy.RestartStrategies;
import org.apache.flink.contrib.streaming.state.EmbeddedRocksDBStateBackend;
import org.apache.flink.streaming.api.CheckpointingMode;
import org.apache.flink.streaming.api.environment.CheckpointConfig;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.table.api.EnvironmentSettings;
import org.apache.flink.table.api.bridge.java.StreamTableEnvironment;
import org.eclipse.jetty.util.StringUtil;

public class StreamTableEnvironmentUtil {

    public static StreamTableEnvironment getTableEnvironment(JobParam jobParam) {
        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
        EnvironmentSettings envSettings = EnvironmentSettings.newInstance().useBlinkPlanner().inStreamingMode().build();
        if (!StringUtil.isEmpty(jobParam.getCheckpoint())) {
            //flink 进行启动一个检查点【设置checkpoint的周期】
            env.enableCheckpointing(jobParam.getEnableCheckpointing());
            env.getCheckpointConfig().setCheckpointingMode(CheckpointingMode.EXACTLY_ONCE);
            // 表示一旦Flink处理程序被cancel后，会保留Checkpoint数据，以便根据实际需要恢复到指定的Checkpoint
            env.getCheckpointConfig().enableExternalizedCheckpoints(CheckpointConfig.ExternalizedCheckpointCleanup.RETAIN_ON_CANCELLATION);
            env.setRestartStrategy(RestartStrategies.fixedDelayRestart(3, 3000L));
            // 检查点必须在时间完成，或者被丢弃【checkpoint的超时时间】
            env.getCheckpointConfig().setCheckpointTimeout(jobParam.getCheckpointTimeout());
            /// 旧的配置 env.setStateBackend(new RocksDBStateBackend());
            env.setStateBackend(new EmbeddedRocksDBStateBackend());
            env.getCheckpointConfig().setCheckpointStorage(jobParam.getCheckpoint());
        }
        StreamTableEnvironment tableEnvironment = StreamTableEnvironment.create(env, envSettings);
        return tableEnvironment;
    }
}
