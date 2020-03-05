package cn.yccoding.gzh.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

/**
 * @Author YC
 * @create 2020/3/5
 * spring中template配置
 */
@Configuration
public class TemplateConfig {

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
    
}
