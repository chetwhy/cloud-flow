package cn.yccoding.pay.form;

import com.alibaba.fastjson.annotation.JSONField;

import lombok.Data;

/**
 * @author : Chet
 * @description : 退款发起请求的实体类
 * @date : 2019/10/29
 */
@Data
public class RefundRequestForm {

    /**
     * 公众账号ID
     */
    private String appid;

    /**
     * 商户号
     */
    @JSONField(name = "mch_id")
    private String mchId;

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

    /**
     * 微信订单号
     */
    @JSONField(name = "transaction_id")
    private String transactionId;

    /**
     * 商户订单号
     */
    @JSONField(name = "out_trade_no")
    private String outTradeNo;

    /**
     * 商户退款单号
     */
    @JSONField(name = "out_refund_no")
    private String outRefundNo;

    /**
     * 订单金额
     */
    @JSONField(name = "total_fee")
    private String totalFee;

    /**
     * 退款金额
     */
    @JSONField(name = "refund_fee")
    private String refundFee;

    /**
     * 退款货币种类
     */
    @JSONField(name = "refund_fee_type")
    private String refundFeeType;

    /**
     * 退款原因
     */
    @JSONField(name = "refund_desc")
    private String refundDesc;

    /**
     * 退款资金来源
     */
    @JSONField(name = "refund_account")
    private String refundAccount;

    /**
     * 退款结果通知url
     */
    @JSONField(name = "notify_url")
    private String notifyUrl;

}