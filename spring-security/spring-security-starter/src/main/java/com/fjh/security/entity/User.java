package com.fjh.security.entity;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;

/**
 * @author fanjh37
 * @since 2023/1/31 19:37
 */
@Data
@AllArgsConstructor
public class User implements Serializable {

    private String name;

    private String password;



}
