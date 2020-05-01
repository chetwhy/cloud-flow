package cn.yccoding.manage.config;

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

    /** send grid **/
    @Bean
    public Queue sendGridQueue() {
        return new Queue(MAIL_SEND_GRID_QUEUE, true, false, false, null);
    }

    @Bean
    public Exchange sendGridExchange() {
        return new TopicExchange(MAIL_SEND_GRID_EXCHANGE, true, false, null);
    }

    @Bean
    public Binding sendGridBinding() {
        return new Binding(MAIL_SEND_GRID_QUEUE, Binding.DestinationType.QUEUE, MAIL_SEND_GRID_EXCHANGE,
            MAIL_SEND_GRID_ROUTING_KEY, null);
    }

    /** 死信 **/
    /*@Bean
    public Queue delayQueue() {
        Map<String,Object> arguments = new HashMap<>();
        arguments.put("x-message-ttl",10*1000);     //  消息时间
        arguments.put("x-dead-letter-exchange","mail.dead.exchange"); //  消息死了交给另一个交换机
        arguments.put("x-dead-letter-routing-key","dead.mail");    // 死信发出去的路由键

        return new Queue("mail.delay.queue", true, false, false, arguments);
    }

    @Bean
    public Queue deadQueue() {
        return new Queue("mail.dead.queue", true, false, false, null);
    }

    @Bean
    public Exchange deadExchange() {
        return new DirectExchange("mail.dead.exchange",true,false,null);
    }

    @Bean
    public Binding delayMailBinding() {
        return new Binding("mail.delay.queue", Binding.DestinationType.QUEUE,"mail.delay.exchange","delay.mail",null);
    }

    @Bean
    public Binding deadMailBinding() {
        return new Binding("mail.dead.queue", Binding.DestinationType.QUEUE,"mail.dead.exchange","dead.mail",null);
    }*/

    /** json输出 **/
    @Bean
    public MessageConverter messageConverter() {
        return new Jackson2JsonMessageConverter();
    }
}
