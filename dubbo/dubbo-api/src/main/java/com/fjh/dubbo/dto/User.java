package com.fjh.dubbo.dto;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 *
 * </p>
 *
 * @author fjh
 * @since 2022/1/28 14:06
 */
@Data
public class User implements Serializable {

    private String id;

    private String name;

    private Integer age;

    private Date time;
}
