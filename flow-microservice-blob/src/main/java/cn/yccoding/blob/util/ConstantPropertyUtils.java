package cn.yccoding.blob.util;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * @Author YC
 * @create 2020/3/24
 * azure配置参数
 */
@Component
//@PropertySource("classpath:application.properties") // 特别指定时使用
public class ConstantPropertyUtils implements InitializingBean {

    @Value("${azure.storage.default-endpoints-protocol}")
    private String defaultEndpointsProtocol;

    @Value("${azure.storage.account-name}")
    private String accountName;

    @Value("${azure.storage.account-key}")
    private String accountKey;

    @Value("${azure.storage.endpoint-suffix}")
    private String endpointSuffix;

    public static String DEFAULT_ENDPOINTS_PROTOCOL;
    public static String ACCOUNT_NAME;
    public static String ACCOUNT_KEY;
    public static String ENDPOINT_SUFFIX;


    @Override
    public void afterPropertiesSet() throws Exception {
        DEFAULT_ENDPOINTS_PROTOCOL = defaultEndpointsProtocol;
        ACCOUNT_NAME = accountName;
        ACCOUNT_KEY = accountKey;
        ENDPOINT_SUFFIX = endpointSuffix;
    }
}
