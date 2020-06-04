package cn.yccoding.mail.util;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * @Author YC
 * @create 2020/4/4
 */
@Component
public class ConstantPropertyUtils implements InitializingBean {

    @Value("${sendgrid.from.name}")
    private String fromName;

    @Value("${sendgrid.from.email}")
    private String fromEmail;

    @Value("${sendgrid.api-key}")
    private String apiKey;

    public static String FROM_NAME;
    public static String FROM_EMAIL;
    public static String API_KEY;

    @Override
    public void afterPropertiesSet() throws Exception {
        FROM_NAME = this.fromName;
        FROM_EMAIL = this.fromEmail;
        API_KEY = this.apiKey;
    }
}
