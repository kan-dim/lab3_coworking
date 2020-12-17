package com.example.demo.config;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MessagingConfig {
    public static final String EXCHANGE = "server";

    public static final String QUEUE_CLIENT = "client";
    public static final String ROUTING_KEY_CLIENT = "client";

    public static final String QUEUE_MANAGER = "manager";
    public static final String ROUTING_KEY_MANAGER = "manager";

    public static final String QUEUE_GUIDE = "guide";
    public static final String ROUTING_KEY_GUIDE = "guide";


    public static final String QUEUE_GROUP = "group";
    public static final String ROUTING_KEY_GROUP = "group";


    @Bean
    public TopicExchange exchange() {
        return new TopicExchange(EXCHANGE);
    }

    @Bean(name = "client")
    public Queue queue_client() {
        return new Queue(QUEUE_CLIENT);
    }

    @Bean(name = "manager")
    public Queue queue_manager() {
        return new Queue(QUEUE_MANAGER);
    }

    @Bean(name = "guide")
    public Queue QUEUE_GUIDE() {
        return new Queue(QUEUE_GUIDE);
    }

    @Bean(name = "group")
    public Queue queue_repair() {
        return new Queue(QUEUE_GROUP);
    }


    @Bean
    public Binding bindingC(@Qualifier("client") Queue queueC, TopicExchange exchange) {
        return BindingBuilder.bind(queueC).to(exchange).with(ROUTING_KEY_CLIENT);
    }

    @Bean
    public Binding bindingM(@Qualifier("manager") Queue queueM, TopicExchange exchange) {
        return BindingBuilder.bind(queueM).to(exchange).with(ROUTING_KEY_MANAGER);
    }

    @Bean
    public Binding bindingT(@Qualifier("guide") Queue queueT, TopicExchange exchange) {
        return BindingBuilder.bind(queueT).to(exchange).with(ROUTING_KEY_GUIDE);
    }

    @Bean
    public Binding bindingR(@Qualifier("group") Queue queueT, TopicExchange exchange) {
        return BindingBuilder.bind(queueT).to(exchange).with(ROUTING_KEY_GROUP);
    }


    @Bean
    public MessageConverter converter() {
        return new Jackson2JsonMessageConverter();
    }

    @Bean
    public AmqpTemplate template(ConnectionFactory connectionFactory) {
        final RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMessageConverter(converter());
        return rabbitTemplate;
    }
}