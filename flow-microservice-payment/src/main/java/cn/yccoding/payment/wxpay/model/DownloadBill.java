package cn.yccoding.payment.wxpay.model;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

/**
 * @author : Chet
 * @description : 下载对账单 实体类
 * @date : 2019/10/30
 */
@Data
public class DownloadBill extends BaseWXPay {

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
