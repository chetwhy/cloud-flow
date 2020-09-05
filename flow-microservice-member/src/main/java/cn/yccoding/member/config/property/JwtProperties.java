package cn.yccoding.member.config.property;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * jwt 配置属性
 *
 * @author YC
 * @since 2020/9/5
 */
@Data
@ConfigurationProperties(prefix = "jwt")
@Component
public class JwtProperties {

    /**
     * 请求头
     */
    private String header;

    /**
     * 加密密钥
     */
    private String secret;

    /**
     * 过期时间
     */
    private Long expire;

    /**
     * token前缀
     */
    private String prefix;
}
