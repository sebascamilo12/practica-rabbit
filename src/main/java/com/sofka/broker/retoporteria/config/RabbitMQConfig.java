package com.sofka.broker.retoporteria.config;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.listener.MessageListenerContainer;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;



@Configuration
public class RabbitMQConfig {

    @Bean
    Queue allApto(){
        return new Queue("allapto", true);
    }

    @Bean
    Queue aptoImpar(){
        return new Queue("aptoimpar", true);
    }
    @Bean
    TopicExchange exchange(){
        return new TopicExchange("topic-exchange");
    }

    @Bean
    Binding aptoImparBinding(Queue aptoImpar, TopicExchange topicExchange){
        return BindingBuilder.bind(aptoImpar).to(topicExchange).with("apto.impar");
    }

    @Bean
    Binding allAptoBinding(Queue allApto, TopicExchange topicExchange){
        return BindingBuilder.bind(allApto).to(topicExchange).with("apto.*");
    }

    @Bean
    public MessageConverter jsonMessageConverter(){
        return new Jackson2JsonMessageConverter();
    }

    @Bean
    MessageListenerContainer messageListenerContainer(ConnectionFactory connectionFactory){
        SimpleMessageListenerContainer simpleMessageListenerContainer = new SimpleMessageListenerContainer();
        simpleMessageListenerContainer.setConnectionFactory(connectionFactory);
        return simpleMessageListenerContainer;
    }

    public AmqpTemplate rabbitTemplate(ConnectionFactory connectionFactory){
        final RabbitTemplate rabbitTemplate =new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMessageConverter(jsonMessageConverter());
        return rabbitTemplate;
    }
}
