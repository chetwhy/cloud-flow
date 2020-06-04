package cn.yccoding.pay.config;

import cn.yccoding.common.contants.ResultCodeEnum;
import cn.yccoding.common.exception.CustomException;
import cn.yccoding.pay.sdk.WXPay;
import cn.yccoding.pay.sdk.WXPayConfigImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author YC
 * @since 2020/6/4 17:44
 */
@Configuration
@Slf4j
public class PaymentConfig {

    @Bean(name = "wxPayDefault")
    public WXPay wxPayDefault() {
        WXPay wxPay = null;
        try {
            wxPay = new WXPay(new WXPayConfigImpl());
        } catch (Exception e) {
            log.error("创建默认wxpay实列失败:[{}]", e.getMessage());
            throw new CustomException(ResultCodeEnum.CREATE_WXPAY_BEAN_FAIL);
        }
        return wxPay;
    }
}
