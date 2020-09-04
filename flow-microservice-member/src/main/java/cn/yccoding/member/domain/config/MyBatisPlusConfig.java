package cn.yccoding.member.domain.config;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * @author YC
 * @since 2020/9/3
 */
@EnableTransactionManagement
@Configuration
@MapperScan("cn.yccoding.member.domain.mapper")
public class MyBatisPlusConfig {
}
