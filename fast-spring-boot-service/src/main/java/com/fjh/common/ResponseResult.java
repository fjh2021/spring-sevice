package com.fjh.common;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@JsonIgnoreProperties(ignoreUnknown = true)
@Data
public class ResponseResult<T> {

    private int code = 0;

    private String msg = "success";
    @JsonProperty(value = "data")
    private T data;

    public static <T> ResponseResult<T> success(T data) {
        ResponseResult<T> rootResult = new ResponseResult<>();
        rootResult.setData(data);
        return rootResult;
    }

    public ResponseResult(T data) {
        this.data = data;
    }

    public ResponseResult() {
    }

    public ResponseResult(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public ResponseResult(int code, T data) {
        this.code = code;
        this.data = data;
    }

    public ResponseResult(int code, String msg, T data) {
        this.code = code;
        this.data = data;
        this.msg = msg;
    }


}