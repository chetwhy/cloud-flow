package cn.yccoding.payment.wxpay.model;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

/**
 * @author : Chet
 * @description : 交易保障 实体类
 * @date : 2019/10/30
 */
@Data
public class Report extends BaseWXPay {

    /**
     * 设备号
     */
    @JSONField(name = "device_info")
    private String deviceInfo;

    /**
     * 接口URL
     */
    @JSONField(name = "interface_url")
    private String interfaceUrl;

    /**
     * 接口耗时
     */
    @JSONField(name = "execute_time")
    private String executeTime;

    /**
     * 返回状态码
     */
    @JSONField(name = "return_code")
    private String returnCode;

    /**
     * 返回信息
     */
    @JSONField(name = "return_msg")
    private String returnMsg;

    /**
     * 业务结果
     */
    @JSONField(name = "result_code")
    private String resultCode;

    /**
     * 错误代码
     */
    @JSONField(name = "err_code")
    private String errCode;

    /**
     * 错误代码描述
     */
    @JSONField(name = "err_code_des")
    private String errCodeDes;

    /**
     * 商户订单号
     */
    @JSONField(name = "out_trade_no")
    private String outTradeNo;

    /**
     * 访问接口IP
     */
    @JSONField(name = "user_ip")
    private String userIp;

    /**
     * 商户上报时间
     */
    private String time;

}
