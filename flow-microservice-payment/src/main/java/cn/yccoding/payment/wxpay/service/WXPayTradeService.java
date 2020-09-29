package cn.yccoding.payment.wxpay.service;

import java.util.Map;

/**
 * 支付服务
 *
 * @author YC
 * @since 2020/9/27
 */
public interface WXPayTradeService {

    /**
     * 统一下单接口，输入指定参数，只关心必要参数
     *
     * @param openid      用户在公众号的唯一识别号
     * @param tradeType   交易类型
     * @param price       价格
     * @param productDesc 商品描述
     * @param terminalIP  终端ip
     * @param requestUrl  请求来源的url
     * @return 返回js校验参数的的map
     */
    Map<String, Object> unifiedorder(String openid, String tradeType, String price, String productDesc,
                                     String terminalIP, String requestUrl);

    /**
     * 订单查询
     *
     * @param outTradeNo
     * @return
     */
    Map<String, String> orderQuery(String outTradeNo);

    /**
     * 关闭订单
     *
     * @param outTradeNo
     * @return
     */
    Map<String, String> closeOrder(String outTradeNo);

    /**
     * 申请退款
     *
     * @param outTradeNo
     * @param outRefundNo
     * @param totalFee
     * @param refundFee
     * @return
     */
    Map<String, String> refund(String outTradeNo, String outRefundNo, String totalFee, String refundFee);

    /**
     * 退款查询
     *
     * @param outTradeNo
     * @return
     */
    Map<String, String> refundQuery(String outTradeNo);

    /**
     * 下载交易账单
     *
     * @param billDate
     * @param billType
     * @return
     */
    Map<String, String> downloadBill(String billDate, String billType);

    /**
     * 下载资金账单
     * @param billDate
     * @param accountType
     * @return
     */
    Map<String, String> downloadFundFlow(String billDate, String accountType);

    /**
     * 交易保障
     *
     * @param interfaceUrl
     * @param executeTime
     * @param returnCode
     * @param returnMsg
     * @param resultCode
     * @param userIp
     * @return
     */
    Map<String, String> report(String interfaceUrl, String executeTime, String returnCode,
                               String returnMsg, String resultCode, String userIp);

    /**
     * 拉取订单评价数据
     *
     * @param beginTime
     * @param endTime
     * @param offset
     * @return
     */
    Map<String, String> batchQueryComment(String beginTime, String endTime, String offset);
}
