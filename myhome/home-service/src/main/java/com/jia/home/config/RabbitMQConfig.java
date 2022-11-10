package com.jia.home.config;
import com.jia.home.utils.MailConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {
    public final static Logger logger = LoggerFactory.getLogger(RabbitMQConfig.class);
    @Autowired
    CachingConnectionFactory cachingConnectionFactory;
    @Bean
    RabbitTemplate rabbitTemplate(){
        RabbitTemplate rabbitTemplate = new RabbitTemplate(cachingConnectionFactory);
        rabbitTemplate.setConfirmCallback((data,ack,cause)->{
            assert data != null;
            String id = data.getId();
            if (ack){
                logger.info("消息发送成功");
                //TODO 记录消息发送成功
            }else{
                logger.info("消息发送失败");
            }
        });
        rabbitTemplate.setReturnsCallback(new RabbitTemplate.ReturnsCallback() {
            @Override
            public void returnedMessage(ReturnedMessage returned) {
                logger.info("消息发送失败");
            }
        });
        rabbitTemplate.setMessageConverter(new Jackson2JsonMessageConverter());
        return rabbitTemplate;
    }
    @Bean
    Queue mailQueue(){
        return new Queue(MailConstants.RABBIT_QUEUE_NAME,true,false,false);
    }
    @Bean
    DirectExchange mailExchange(){
        return new DirectExchange(MailConstants.RABBIT_EXCHANGE_NAME,true,false);
    }
    @Bean
    Binding mailBing(){
        return BindingBuilder.bind(mailQueue()).to(mailExchange()).with(MailConstants.RABBIT_ROUTING_KEY_NAME);
    }
}
