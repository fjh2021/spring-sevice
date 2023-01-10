package com.fjh.business.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * <p>
 *
 * </p>
 *
 * @author fjh
 * @since 2022/3/30 15:45
 */
@Data
@TableName("my_base")
public class MBaseEntity {

    private Long id;

    private String name;
}
