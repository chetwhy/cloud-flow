package cn.yccoding.member.config.property;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.LinkedHashSet;

/**
 * 不拦截的url参数
 *
 * @author YC
 * @since 2020/9/4
 */
@Data
@Component
@ConfigurationProperties(prefix = "member.auth")
public class NoAuthUrlProperties {

    private LinkedHashSet<String> shouldSkipUrls;
}
