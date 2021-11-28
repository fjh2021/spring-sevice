package com.fjh.flink.util;

import com.fjh.flink.param.JobParam;
import org.apache.flink.api.java.utils.ParameterTool;
import org.codehaus.plexus.util.StringUtils;

import java.util.Properties;

public class JobParamUtil {


    public static JobParam initJobParam(String[] args) throws Exception {
        ParameterTool parameters = ParameterTool.fromArgs(args);
        String filePath = parameters.get("path", null);
        return initJobParam(filePath);
    }

    public static JobParam initJobParam(String configFile) throws Exception {
        if (StringUtils.isEmpty(configFile)) {
            throw new Exception("job config file should not null");
        }
        JobParam jobParam = new JobParam();
        Properties properties = PropertiesUtils.getProperties(configFile);
        String jobName = properties.getProperty("job.name");
        String checkpoint = properties.getProperty("job.checkpoint");
        String sql = properties.getProperty("job.sql");

        jobParam.setJobName(jobName);
        jobParam.setCheckpoint(checkpoint);

        jobParam.setSqlArray(initSqlArray(sql));

        return jobParam;
    }

    private static String[] initSqlArray(String sql) throws Exception {
        if (StringUtils.isEmpty(sql)) {
            throw new Exception("job config file sql param should not null");
        }
        return sql.split("\\;");

    }


}
