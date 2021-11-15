package com.fjh.flink;

import com.fjh.flink.param.JobParam;
import com.fjh.flink.util.JobParamUtil;
import com.fjh.flink.util.StreamTableEnvironmentUtil;
import org.apache.flink.table.api.bridge.java.StreamTableEnvironment;
import org.eclipse.jetty.util.StringUtil;

public class FlinkCDC {

    public static void main(String[] args) throws Exception {

        JobParam jobParam = JobParamUtil.initJobParam(args);
        StreamTableEnvironment tableEnvironment = StreamTableEnvironmentUtil.getTableEnvironment(jobParam);
        //任务名
        if (!StringUtil.isEmpty(jobParam.getJobName())) {
            tableEnvironment.getConfig().getConfiguration().setString("pipeline.name", jobParam.getJobName());
        }
        //执行sql
        if (jobParam.getSqlArray() != null && jobParam.getSqlArray().length > 0) {
            for (String sql : jobParam.getSqlArray()) {
                tableEnvironment.executeSql(sql);
            }
        }
    }

    

}

