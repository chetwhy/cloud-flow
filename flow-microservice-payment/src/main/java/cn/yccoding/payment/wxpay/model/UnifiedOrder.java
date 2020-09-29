package cn.yccoding.payment.wxpay.model;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * @author : Chet
 * @description : 所有微信支付使用的实体类
 * @date : 2019/10/29
 */
@Data
@Accessors(chain = true)
public class UnifiedOrder extends BaseWXPay {

    /**
     * 设备号
     */
    @JSONField(name = "device_info")
    private String deviceInfo;

    /**
     * 商品描述
     */
    private String body;

    /**
     * 商品详情
     */
    private String detail;

    /**
     * 附加数据
     */
    private String attach;

    /**
     * 商户订单号
     */
    @JSONField(name = "out_trade_no")
    private String outTradeNo;

    /**
     * 标价币种
     */
    @JSONField(name = "fee_type")
    private String feeType;

    /**
     * 标价金额
     */
    @JSONField(name = "total_fee")
    private String totalFee;

    /**
     * 终端IP
     */
    @JSONField(name = "spbill_create_ip")
    private String spbillCreateIp;

    /**
     * 交易起始时间
     */
    @JSONField(name = "time_start")
    private String timeStart;

    /**
     * 交易结束时间
     */
    @JSONField(name = "time_expire")
    private String timeExpire;

    /**
     * 订单优惠标记
     */
    @JSONField(name = "goods_tag")
    private String goodsTag;

    /**
     * 通知地址
     */
    @JSONField(name = "notify_url")
    private String notifyUrl;

    /**
     * 交易类型
     */
    @JSONField(name = "trade_type")
    private String tradeType;

    /**
     * 商品ID
     */
    @JSONField(name = "product_id")
    private String productId;

    /**
     * 指定支付方式
     */
    @JSONField(name = "limit_pay")
    private String limitPay;

    /**
     * 用户标识
     */
    private String openid;

    /**
     * 电子发票入口开放标识
     */
    private String receipt;

    /**
     * 场景信息
     */
    @JSONField(name = "scene_info")
    private SceneInfo sceneInfo;

    @Override
    public UnifiedOrder setSign(String sign) {
        return (UnifiedOrder) super.setSign(sign);
    }

    @Override
    public UnifiedOrder setNonceStr(String nonceStr) {
        return (UnifiedOrder) super.setNonceStr(nonceStr);
    }

    @Override
    public UnifiedOrder setSignType(String signType) {
        return (UnifiedOrder) super.setSignType(signType);
    }
}
