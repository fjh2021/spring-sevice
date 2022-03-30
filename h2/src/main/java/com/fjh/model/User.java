package com.fjh.model;

import lombok.Data;

import java.io.Serializable;

/**
 * <p>
 *
 * </p>
 *
 * @author fjh
 * @since 2022/2/11 9:56
 */
@Data
public class User implements Serializable {

    private Long id;
    private String name;
    private Integer age;
    private String email;
}
