package com.example.eventdrivennasa.configuration;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Exchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.QueueBuilder;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitConfiguration {


    @Bean
    public MessageConverter messageConverter() {
        return new Jackson2JsonMessageConverter();
    }

    @Bean
    public Exchange pictureExchange() {
        return new DirectExchange("pictures-direct-exchange");
    }

    @Bean
    public Queue picturesQueue() {
        return QueueBuilder.durable("pictures-queue")
          .withArgument("x-dead-letter-exchange", "")
          .withArgument("x-dead-letter-routing-key", "pictures-dlq")
          .build();
    }

    @Bean
    public Queue picturesDlq() {
        return new Queue("pictures-dlq");
    }

    @Bean
    public Binding picturesQueueBinding() {
        return BindingBuilder
          .bind(picturesQueue())
          .to(pictureExchange())
          .with("")
          .noargs();
    }


}
