package com.houdask.site.common.mq.activemq;

import javax.jms.Destination;
  
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.jms.core.JmsMessagingTemplate;
import org.springframework.stereotype.Service;  
  
@Service
public class ActiveMQDemoProducer/* implements CommandLineRunner*/ {
    @Autowired // 也可以注入JmsTemplate，JmsMessagingTemplate对JmsTemplate进行了封装  
    private JmsMessagingTemplate jmsTemplate;  
    // 发送消息，destination是发送到的队列，message是待发送的消息  
    public void sendMessage(Destination destination, final String message){  
        jmsTemplate.convertAndSend(destination, message);  
    }

  /*  @Override
    public void run(String... args) throws Exception {
        sendMessage("Sample message");
        System.out.println("Message was sent to the Queue");
    }*/
}