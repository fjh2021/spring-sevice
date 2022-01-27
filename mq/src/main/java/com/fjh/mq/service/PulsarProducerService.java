package com.fjh.mq.service;

import com.fjh.mq.dto.MessageDto;
import io.github.majusko.pulsar.producer.PulsarTemplate;
import lombok.extern.slf4j.Slf4j;
import org.apache.pulsar.client.api.PulsarClientException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 *
 * </p>
 *
 * @author fjh
 * @since 2022/1/21 14:17
 */
@Service
@Slf4j
public class PulsarProducerService {

    @Autowired
    private PulsarTemplate template;

    public void send(MessageDto message) {
        try {
            template.send("bootTopic", message);
        } catch (PulsarClientException e) {
            log.info("--------------------{}",e.getMessage());
            e.printStackTrace();
        }
    }

}
