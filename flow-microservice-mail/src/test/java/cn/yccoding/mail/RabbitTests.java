package cn.yccoding.mail;

import cn.yccoding.common.form.UserForm;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.amqp.core.AmqpAdmin;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @Author YC
 * @create 2020/4/4
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class RabbitTests {

    @Autowired
    RabbitTemplate rabbitTemplate;

    @Autowired
    AmqpAdmin amqpAdmin;

    // 测试发送，direct模式
    @Test
    public void testSend() {
        UserForm user = new UserForm();
        user.setEmail("123@qq.com");
        user.setUsername("王二狗");
        //rabbitTemplate.setMessageConverter(new Jackson2JsonMessageConverter());
        rabbitTemplate.convertAndSend("direct-exchange01","key01",user);

        System.out.println("消息发送完成...");
    }

    // 声明一个队列
    @Test
    public void createQueue() {
        Queue queue = new Queue("java-queue01",true,false,false);
        amqpAdmin.declareQueue(queue);
        System.out.println("创建完成");
    }

    // 声明一个交换机
    @Test
    public void createExchange() {
        DirectExchange exchange = new DirectExchange("java-direct-exchange01",true,false);
        amqpAdmin.declareExchange(exchange);
        System.out.println("创建完成");
    }

    // 绑定队列
    @Test
    public void createBinding() {
        Binding binding = new Binding("java-queue01", Binding.DestinationType.QUEUE,"java-direct-exchange01","java-key01",null);
        amqpAdmin.declareBinding(binding);
        System.out.println("绑定完成");
    }
}
