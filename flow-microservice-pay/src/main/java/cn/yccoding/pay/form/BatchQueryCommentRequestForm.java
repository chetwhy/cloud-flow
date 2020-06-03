package cn.yccoding.pay.form;

import com.alibaba.fastjson.annotation.JSONField;

import lombok.Data;

/**
 * @author : Chet
 * @description : 拉取订单评价数据实体类
 * @date : 2019/11/5
 */
@Data
public class BatchQueryCommentRequestForm {

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
     * 商户订单号
     */
    @JSONField(name = "out_trade_no")
    private String outTradeNo;

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
     * 开始时间
     */
    @JSONField(name = "begin_time")
    private String beginTime;

    /**
     * 结束时间
     */
    @JSONField(name = "end_time")
    private String endTime;

    /**
     * 位移
     */
    private String offset;

    /**
     * 条数
     */
    private String limit;
}
