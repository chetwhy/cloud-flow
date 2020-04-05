package cn.yccoding.manage;

import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @Author YC
 * @create 2020/4/4
 */
@SpringBootApplication
@EnableRabbit
//@ComponentScan(basePackages = {"cn.yccoding.common","cn.yccoding.manage"})
public class ManageApplication {

    public static void main(String[] args) {
        SpringApplication.run(ManageApplication.class, args);
    }
}
