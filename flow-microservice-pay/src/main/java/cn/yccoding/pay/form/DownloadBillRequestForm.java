package cn.yccoding.pay.form;

import com.alibaba.fastjson.annotation.JSONField;

import lombok.Data;

/**
 * @author : Chet
 * @description : 下载对账单 实体类
 * @date : 2019/10/30
 */
@Data
public class DownloadBillRequestForm {

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
     * 对账单日期
     */
    @JSONField(name = "bill_date")
    private String billDate;

    /**
     * 账单类型
     */
    @JSONField(name = "bill_type")
    private String billType;

    /**
     * 压缩账单
     */
    @JSONField(name = "tar_type")
    private String tarType;
}
