package cn.yccoding.payment.property;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * key
 *
 * @author YC
 * @since 2020/9/27
 */
@Data
@ConfigurationProperties(prefix = "redis.key")
public class RedisKeyProperties {

    private RedisKeyProperties.Prefix prefix;

    private RedisKeyProperties.Expire expire;

    @Data
    public static class Prefix {
        private String wxToken;
    }

    @Data
    public static class Expire {
        private Long wxToken;
    }
}
