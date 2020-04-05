package cn.yccoding.mail.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.Exchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import static cn.yccoding.common.contants.MqConstant.*;

/**
 * @Author YC
 * @create 2020/4/4 rabbitmq配置类
 */
@Configuration
public class RabbitmqConfig {

    /** 邮件 **/
    @Bean
    public Queue mailQueue() {
        return new Queue(MAIL_REGISTER_QUEUE, true, false, false, null);
    }

    @Bean
    public Exchange mailExchange() {
        return new TopicExchange(MAIL_REGISTER_EXCHANGE, true, false, null);
    }

    @Bean
    public Binding orderBinding() {
        return new Binding(MAIL_REGISTER_QUEUE, Binding.DestinationType.QUEUE, MAIL_REGISTER_EXCHANGE,
            MAIL_REGISTER_ROUTING_KEY, null);
    }

    /** json输出 **/
    @Bean
    public MessageConverter messageConverter() {
        return new Jackson2JsonMessageConverter();
    }
}
