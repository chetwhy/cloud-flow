package cn.yccoding.payment.wxpay.model;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

/**
 * @author : Chet
 * @description : 下载资金账单 实体类
 * @date : 2019/10/30
 */
@Data
public class DownloadFundFlow extends BaseWXPay {

    /**
     * 资金账单日期
     */
    @JSONField(name = "bill_date")
    private String billDate;

    /**
     * 资金账户类型
     */
    @JSONField(name = "account_type")
    private String accountType;

    /**
     * 压缩账单
     */
    @JSONField(name = "tar_type")
    private String tarType;

}
