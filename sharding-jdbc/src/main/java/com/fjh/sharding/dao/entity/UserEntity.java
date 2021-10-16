package com.fjh.sharding.dao.entity;

import java.io.Serializable;

/**
 * @Author fjh
 * @Email
 * @Date 2021/8/24 22:30
 * @Description
 **/
public class UserEntity implements Serializable {

    private String tName;

    private String tNameSeq;

    public String gettName() {
        return tName;
    }

    public void settName(String tName) {
        this.tName = tName;
    }

    public String gettNameSeq() {
        return tNameSeq;
    }

    public void settNameSeq(String tNameSeq) {
        this.tNameSeq = tNameSeq;
    }
}
