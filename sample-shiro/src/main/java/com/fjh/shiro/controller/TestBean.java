package com.fjh.shiro.controller;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

/**
 * @Author 范建豪
 * @Email
 * @Date 2021/7/28 14:20
 * @Description
 **/
@ApiModel
public class TestBean implements Serializable {

    @ApiModelProperty(name="名字",value = "11")
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
