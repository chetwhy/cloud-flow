package cn.yccoding.payment.wxpay.model;

import cn.yccoding.payment.wxpay.config.WXPayConfigs;
import com.alibaba.fastjson.annotation.JSONField;
import lombok.Getter;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * 基础类
 *
 * @author YC
 * @since 2020/9/27
 */
@Getter
@Accessors(chain = true)
public class BaseWXPay implements Serializable {

    /**
     * 公众账号ID
     */
    private final String appid = WXPayConfigs.getAppId();

    /**
     * 商户号
     */
    @JSONField(name = "mch_id")
    private final String mchId = WXPayConfigs.getMuchId();

    /**
     * 随机字符串
     */
    @JSONField(name = "nonce_str")
    private String nonceStr;

    /**
     * 签名
     */
    private String sign;


    /**
     * 签名类型
     */
    @JSONField(name = "sign_type")
    private String signType;

    public BaseWXPay setNonceStr(String nonceStr) {
        this.nonceStr = nonceStr;
        return this;
    }

    public BaseWXPay setSign(String sign) {
        this.sign = sign;
        return this;
    }

    public BaseWXPay setSignType(String signType) {
        this.signType = signType;
        return this;
    }
}
