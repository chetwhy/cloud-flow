package cn.yccoding.payment.alipay.service;

import cn.yccoding.payment.alipay.model.TradePaySummary;
import cn.yccoding.payment.alipay.sdk.model.result.AlipayF2FQueryResult;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * trade
 *
 * @author YC
 * @since 2020/9/29
 */
public interface TradeService {


    /**
     * 当面付2.0支付
     *
     * @param tradePaySummary
     * @return
     */
    String f2fPayTradePay(@RequestBody TradePaySummary tradePaySummary);

    /**
     * 当面付2.0查询订单
     * @param outTradeNo 商户订单号
     */
    AlipayF2FQueryResult f2fTradeQuery(String outTradeNo);
}
