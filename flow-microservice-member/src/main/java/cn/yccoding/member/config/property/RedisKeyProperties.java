package cn.yccoding.member.config.property;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * redis属性文件配置
 *
 * @author YC
 * @since 2020/9/3
 */
@Data
@Component
@ConfigurationProperties(prefix = "redis.key")
public class RedisKeyProperties {
    private RedisKeyProperties.Prefix prefix;

    private RedisKeyProperties.Expire expire;

    @Data
    public static class Prefix {
        private String otpCode;

    }

    @Data
    public static class Expire {
        private Long otpCode;

    }
}
