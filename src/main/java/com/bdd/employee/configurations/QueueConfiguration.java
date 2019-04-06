package com.bdd.employee.configurations;

import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class QueueConfiguration {
    public static final String QUEUE_NAME = "employeeQueue";

    @Bean
    public Queue employeeQueue() {
        return new Queue(QUEUE_NAME);
    }
}
