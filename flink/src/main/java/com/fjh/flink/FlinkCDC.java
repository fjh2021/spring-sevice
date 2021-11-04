package com.fjh.flink;

import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.table.api.EnvironmentSettings;
import org.apache.flink.table.api.bridge.java.StreamTableEnvironment;
import org.eclipse.jetty.util.StringUtil;

public class FlinkCDC {

    public static void main(String[] args) {
        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
        EnvironmentSettings envSettings = EnvironmentSettings.newInstance().useBlinkPlanner().inStreamingMode().build();
        env.enableCheckpointing(60000);
        StreamTableEnvironment tableEnvironment = StreamTableEnvironment.create(env, envSettings);
        String jobName="";
        if (!StringUtil.isEmpty(jobName)) {
            tableEnvironment.getConfig().getConfiguration().setString("pipeline.name", jobName);
        }

        tableEnvironment.executeSql(" CREATE TABLE source (\n" +
                "          `order_id` INTEGER ,\n" +
                "          `order_date` DATE ,\n" +
                "          `order_time` TIMESTAMP(3),\n" +
                "          `quantity` INT ,\n" +
                "          `product_id` INT ,\n" +
                "          `purchaser` STRING,\n" +
                "           primary key(order_id)  NOT ENFORCED" +
                "         ) WITH (\n" +
                "          'connector' = 'mysql-cdc',\n" +
                "          'hostname' = 'localhost',\n" +
                "          'port' = '3306',\n" +
                "          'username' = 'root',\n" +
                "          'password' = '123456',\n" +
                "          'database-name' = 'flink',\n" +
                "          'table-name' = 'flink_cdc_test'," +
                //  全量 + 增量同步
                "          'scan.startup.mode' = 'initial'      " +
                " )");
        tableEnvironment.executeSql("CREATE TABLE sink (\n" +
                "          `order_id` INTEGER ,\n" +
                "          `order_date` DATE ,\n" +
                "          `order_time` TIMESTAMP(3),\n" +
                "          `quantity` INT ,\n" +
                "          `product_id` INT ,\n" +
                "          `purchaser` STRING,\n" +
                "          primary key (order_id)  NOT ENFORCED " +
                ") WITH (\n" +
                "    'connector' = 'jdbc',\n" +
                "    'driver' = 'com.mysql.cj.jdbc.Driver',\n" +
                "    'url' = 'jdbc:mysql://localhost:3306/flink?useSSL=false&autoReconnect=true&useUnicode=true&characterEncoding=utf8&serverTimezone=Asia/Shanghai',\n" +
                "    'username' = 'root',\n" +
                "    'password' = '123456',\n" +
                "    'table-name' = 'flink_cdc_test_sink' " +
                ")");

        tableEnvironment.executeSql("insert into sink select * from source");
    }
}

