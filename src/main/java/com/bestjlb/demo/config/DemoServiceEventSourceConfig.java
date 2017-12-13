package com.bestjlb.demo.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Component;

/**
 * Created by yydx811 on 2017/10/26.
 */
@Component
public class DemoServiceEventSourceConfig {

    private static Logger logger = LoggerFactory.getLogger(DemoServiceEventSourceConfig.class);

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Bean
    private DemoEventSource eventSource() {
        return new DemoEventSource(rabbitTemplate);
    }

    public static class DemoEventSource {

        private final RabbitTemplate rabbitTemplate;

        public DemoEventSource(RabbitTemplate rabbitTemplate) {
            this.rabbitTemplate = rabbitTemplate;
        }

        public void publishToDemo(String message) {
            logger.info("publish to demo : {}.", message);
            rabbitTemplate.convertAndSend("queue.demo", MessageBuilder.withPayload(message).build());
        }
    }
}
