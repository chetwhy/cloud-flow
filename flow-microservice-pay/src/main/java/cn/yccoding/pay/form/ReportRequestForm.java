package cn.yccoding.pay.form;

import com.alibaba.fastjson.annotation.JSONField;

import lombok.Data;

/**
 * @author : Chet
 * @description : 交易保障 实体类
 * @date : 2019/10/30
 */
@Data
public class ReportRequestForm {

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
     * 设备号
     */
    @JSONField(name = "device_info")
    private String deviceInfo;

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
