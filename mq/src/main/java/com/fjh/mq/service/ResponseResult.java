package com.fjh.mq.service;

import lombok.Data;

import java.io.Serializable;

/**
 * <p>
 *
 * </p>
 *
 * @author fjh
 * @since 2022/1/21 14:31
 */
@Data
public class ResponseResult implements Serializable {

    private Object object;

    public static ResponseResult success(Object object) {
        ResponseResult result = new ResponseResult();
        result.setObject(object);
        return result;
    }
}
