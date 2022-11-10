package com.jia.thirdservice.rabbit;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jia.home.utils.MailConstants;
import com.jia.home.model.User;
import com.rabbitmq.client.Channel;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class MailReceiver {
    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    JavaMailSender javaMailSender;

    @RabbitListener(queues = MailConstants.RABBIT_QUEUE_NAME)
    public void sendMail(Message message, Channel channel) throws IOException {
        byte[] msg = message.getBody();
        long deliveryTag = message.getMessageProperties().getDeliveryTag();
        User user = objectMapper.readValue(msg, User.class);
        channel.basicAck(deliveryTag,true);
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setTo(user.getUserEmail());
        mailMessage.setFrom("2307497284@qq.com");
        mailMessage.setSubject("问候");
        mailMessage.setText("欢迎入职！");
        javaMailSender.send(mailMessage);

    }
}
