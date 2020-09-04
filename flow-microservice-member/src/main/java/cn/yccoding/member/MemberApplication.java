package cn.yccoding.member;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

/**
 * 启动类
 *
 * @author YC
 * @since 2020/9/3
 */
@SpringBootApplication
@ComponentScan(basePackages = {"cn.yccoding.common","cn.yccoding.member"})
public class MemberApplication {

    public static void main(String[] args) {
        SpringApplication.run(MemberApplication.class, args);
    }
}
