package cn.yccoding.gzh;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

/**
 * @Author YC
 * @create 2020/3/3
 */
@SpringBootApplication
@ComponentScan(basePackages = {"cn.yccoding.common","cn.yccoding.gzh"})
public class GzhApplication {
    public static void main(String[] args) {
        SpringApplication.run(GzhApplication.class, args);
    }
}
