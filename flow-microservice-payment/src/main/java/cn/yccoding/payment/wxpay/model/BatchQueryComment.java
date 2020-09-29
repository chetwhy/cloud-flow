package cn.yccoding.payment.wxpay.model;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

/**
 * @author : Chet
 * @description : 拉取订单评价数据实体类
 * @date : 2019/11/5
 */
@Data
public class BatchQueryComment extends BaseWXPay {

    /**
     * 商户订单号
     */
    @JSONField(name = "out_trade_no")
    private String outTradeNo;

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
