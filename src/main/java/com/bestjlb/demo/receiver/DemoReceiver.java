package com.bestjlb.demo.receiver;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

/**
 * Created by yydx811 on 2017/10/26.
 */
@Component
public class DemoReceiver {

    private static Logger logger = LoggerFactory.getLogger(DemoReceiver.class);

    @Bean
    public Queue demoQueue() {
        return new Queue("queue.demo", true, false, false);
    }

    @RabbitListener(queues = "queue.demo")
    public void demoQueueHandler(String message) {
        logger.info("receive from demo {}.", message);
    }
}
