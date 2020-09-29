package cn.yccoding.authcenter.property;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * jwt证书
 *
 * @author YC
 * @since 2020/9/25
 */
@Data
@ConfigurationProperties(prefix = "flow.jwt")
public class JwtCAProperties {
    /**
     * 证书名称
     */
    private String keyPairName;

    /**
     * 证书别名
     */
    private String keyPairAlias;

    /**
     * 证书私钥
     */
    private String keyPairSecret;

    /**
     * 证书存储密钥
     */
    private String keyPairStoreSecret;
}
