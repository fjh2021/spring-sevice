package com.fjh.mq.config;

import com.fjh.mq.dto.MessageDto;
import io.github.majusko.pulsar.producer.ProducerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * <p>
 *
 * </p>
 *
 * @author fjh
 * @since 2022/1/21 14:12
 */
@Configuration
public class PulsarConfig {

    @Bean
    public ProducerFactory producerFactory() {
        return new ProducerFactory()
                .addProducer("bootTopic", MessageDto.class)
                .addProducer("stringTopic", String.class);
    }
}
