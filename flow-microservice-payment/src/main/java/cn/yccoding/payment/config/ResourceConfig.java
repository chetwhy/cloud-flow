package cn.yccoding.payment.config;

import cn.yccoding.payment.property.AliPayTradeProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * 资源配置
 *
 * @author YC
 * @since 2020/9/29
 */
@Configuration
public class ResourceConfig implements WebMvcConfigurer {

    @Autowired
    private AliPayTradeProperties aliPayTradeProperties;

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        String os = System.getProperty("os.name");
        // 将http请求路径映射到本地路径
        if (os.toLowerCase().startsWith("win")) {
            registry.addResourceHandler(aliPayTradeProperties.getQrcode().getHttpBasePath() + "/**")
                    .addResourceLocations("file:" + aliPayTradeProperties.getQrcode().getStorePath() + "/");
        } else {
            registry.addResourceHandler(aliPayTradeProperties.getQrcode().getHttpBasePath() + "/**")
                    .addResourceLocations("file:" + aliPayTradeProperties.getQrcode().getStorePath() + "/");
        }
    }
}
