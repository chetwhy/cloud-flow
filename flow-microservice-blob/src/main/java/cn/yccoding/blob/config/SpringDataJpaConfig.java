package cn.yccoding.blob.config;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.dao.annotation.PersistenceExceptionTranslationPostProcessor;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * @Author YC
 * @create 2020/3/7
 * jpa配置
 */
@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(basePackages = "cn.**.repository")
@EntityScan(basePackages = "cn.**.domain")
public class SpringDataJpaConfig {

    @Bean
    PersistenceExceptionTranslationPostProcessor persistenceExceptionTranslationPostProcessor() {
        return new PersistenceExceptionTranslationPostProcessor();
    }
}
