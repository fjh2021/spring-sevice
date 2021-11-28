package com.fjh.flink.param;

public class JobParam {
    private String configFile;
    private String jobName;
    private String checkpoint;
    private Long enableCheckpointing = 60000L;
    private Long checkpointTimeout = 600000L;
    private String sql;
    private String[] sqlArray;

    public String getConfigFile() {
        return configFile;
    }

    public void setConfigFile(String configFile) {
        this.configFile = configFile;
    }

    public String getJobName() {
        return jobName;
    }

    public void setJobName(String jobName) {
        this.jobName = jobName;
    }

    public String getCheckpoint() {
        return checkpoint;
    }

    public void setCheckpoint(String checkpoint) {
        this.checkpoint = checkpoint;
    }

    public String getSql() {
        return sql;
    }

    public void setSql(String sql) {
        this.sql = sql;
    }

    public String[] getSqlArray() {
        return sqlArray;
    }

    public void setSqlArray(String[] sqlArray) {
        this.sqlArray = sqlArray;
    }

    public Long getEnableCheckpointing() {
        return enableCheckpointing;
    }

    public void setEnableCheckpointing(Long enableCheckpointing) {
        this.enableCheckpointing = enableCheckpointing;
    }

    public Long getCheckpointTimeout() {
        return checkpointTimeout;
    }

    public void setCheckpointTimeout(Long checkpointTimeout) {
        this.checkpointTimeout = checkpointTimeout;
    }
}
