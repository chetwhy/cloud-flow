package cn.yccoding.payment.wxpay.model;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

/**
 * @author : Chet
 * @description : 关闭订单的实体类
 * @date : 2019/10/29
 */
@Data
public class CloseOrder extends BaseWXPay {

    /**
     * 商户订单号
     */
    @JSONField(name = "out_trade_no")
    private String outTradeNo;

}
