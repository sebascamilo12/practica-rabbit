package com.sofka.broker.retoporteria.controller;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value ="/apartamentos")
public class RabbitMQController {

    @Autowired
    private AmqpTemplate amqpTemplate;

    @GetMapping(value = "/allApto")
    public String allPato(@RequestParam("exchangeName") String topicExchange,
                           @RequestParam("routingKey") String routingKey,
                           @RequestParam("messageData") String messageData){
        amqpTemplate.convertAndSend(topicExchange, routingKey, messageData);
        return "Message sent to all Aptos";
    }

    @GetMapping(value = "/aptoImpar")
    public String aptoImpar(@RequestParam("exchangeName") String topicExchange,
                           @RequestParam("routingKey") String routingKey,
                           @RequestParam("messageData") String messageData){
        amqpTemplate.convertAndSend(topicExchange, routingKey, messageData);
        return "Message sent to Impar Aptos";
    }
}
