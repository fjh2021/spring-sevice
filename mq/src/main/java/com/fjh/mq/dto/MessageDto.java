package com.fjh.mq.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * <p>
 *
 * </p>
 *
 * @author fjh
 * @since 2022/1/21 14:28
 */
@Data
public class MessageDto implements Serializable {

    private Long id;

    private String content;
}
