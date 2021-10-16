package com.fjh.sharding.dao;

import com.fjh.sharding.dao.entity.UserEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @Author fjh
 * @Email
 * @Date 2021/8/24 22:29
 * @Description
 **/
@Mapper
@Component
public interface UserMapper {

     @Select("select * from t_n")
     List<UserEntity> getUserEntity();
}
