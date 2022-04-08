package com.fjh.common;

import lombok.Data;

/**
 * <p>
 *
 * </p>
 *
 * @author fjh
 * @since 2022/4/8 10:49
 */
@Data
public class MyException extends RuntimeException {

    private int code;

    private String msg;
}
