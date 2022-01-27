package com.fjh.mq.service;

import com.fjh.mq.dto.MessageDto;
import io.github.majusko.pulsar.annotation.PulsarConsumer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * <p>
 *
 * </p>
 *
 * @author fjh
 * @since 2022/1/21 14:18
 */
@Service
@Slf4j
public class PulsarRealConsumerService {

    @PulsarConsumer(topic = "bootTopic", clazz = MessageDto.class)
    public void consume(MessageDto message) {
        log.info("PulsarRealConsumer consume id:{},content:{}", message.getId(), message.getContent());
    }
}
