package cn.yccoding.mail;

import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @Author YC
 * @create 2020/4/4
 */
@SpringBootApplication
@EnableRabbit
//@ComponentScan(basePackages = {"cn.yccoding.common"})
public class MailApplication {

    public static void main(String[] args) {
        SpringApplication.run(MailApplication.class, args);
    }
}
