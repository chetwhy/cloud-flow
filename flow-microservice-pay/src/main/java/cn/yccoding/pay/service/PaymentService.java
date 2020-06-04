package cn.yccoding.pay.service;

import cn.yccoding.common.util.CurrencyUtils;
import cn.yccoding.pay.form.*;
import cn.yccoding.pay.sdk.PaymentConstants;
import cn.yccoding.pay.sdk.WXPay;
import cn.yccoding.pay.sdk.WXPayUtil;
import cn.yccoding.pay.util.SignatureUtils;
import com.alibaba.fastjson.JSON;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

import static cn.yccoding.pay.util.ConstantPropertyUtils.*;

/**
 * @author YC
 * @since 2020/6/3 18:03
 */
@Service
public class PaymentService {

    @Autowired
    private WXPay wxPay;

    /**
     * 统一下单接口，输入指定参数，只关心必要参数
     * @param openid        用户在公众号的唯一识别号
     * @param tradeType     交易类型
     * @param price         价格
     * @param productDesc   商品描述
     * @param terminalIP    终端ip
     * @param requestUrl    请求来源的url
     * @return  返回js校验参数的的map
     */
    public Map<String, Object> unifiedorder(String openid, String tradeType, String price, String productDesc,
                                            String terminalIP, String requestUrl) {
        try {
            UnifiedOrderRequestForm requestEntity = new UnifiedOrderRequestForm();
            requestEntity.setBody(productDesc);
            requestEntity.setOutTradeNo(generateRandomOrderNo());
            requestEntity.setTotalFee(CurrencyUtils.Yuan2Fen(Double.parseDouble(price)).toString());
            requestEntity.setSpbillCreateIp(terminalIP);
            requestEntity.setOpenid(openid);
            requestEntity.setTradeType(tradeType);
            String nonceStr = WXPayUtil.generateNonceStr();
            requestEntity.setNonceStr(nonceStr);
            requestEntity.setNotifyUrl(NOTIFY_URL);

            // 利用sdk统一下单,已自动调用wxpay.fillRequestData(data);
            Map<String, String> respMap = wxPay.unifiedOrder(beanToMap(requestEntity));

            // 统一下单接口调用成功
            if (respMap.get("return_code").equals(PaymentConstants.SUCCESS)
                    && respMap.get("result_code").equals((PaymentConstants.SUCCESS))) {
                String prepayId = respMap.get("prepay_id");
                return SignatureUtils.permissionValidate(APP_ID, nonceStr, requestUrl, prepayId,
                        API_KEY,tokenService.getJsApiTicket(tokenService.getAccessToken(APP_ID)));
            } else if (!respMap.get("return_code").equals(PaymentConstants.SUCCESS)) {
                Map<String, Object> map = new HashMap<>();
                for (String key : respMap.keySet()) {
                    map.put(key, respMap.get(key));
                }
                return map;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Autowired
    private PaymentService paymentService;

    @Autowired
    private TokenService tokenService;

    /**
     * 通用微信支付的调用方法，参数灵活
     * @param requestForm UnifiedOrderRequestEntity统一下单的实体类
     * @param requestUrl    请求来源的url
     * @return
     */
    public Map<String, Object> unifiedorder(UnifiedOrderRequestForm requestForm, String requestUrl) {
        try {
            String nonceStr = requestForm.getNonceStr();
            Map<String, String> respMap = wxPay.unifiedOrder(beanToMap(requestForm));

            // 统一下单接口调用成功
            if (respMap.get("return_code").equals(PaymentConstants.SUCCESS)
                    && respMap.get("result_code").equals((PaymentConstants.SUCCESS))) {
                String prepayId = respMap.get("prepay_id");
                return SignatureUtils.permissionValidate(APP_ID, nonceStr, requestUrl, prepayId,
                        API_KEY,tokenService.getJsApiTicket(tokenService.getAccessToken(APP_ID)));
            } else if (!respMap.get("return_code").equals(PaymentConstants.SUCCESS)) {
                Map<String, Object> map = new HashMap<>();
                for (String key : respMap.keySet()) {
                    map.put(key, respMap.get(key));
                }
                return map;
            }
        } catch (Exception e) {
            e.printStackTrace(); // 返回包含错误提示的map
        }
        return null;
    }

    public Map<String, String> orderQuery(String outTradeNo) {
        try {
            OrderQueryRequestForm requestForm = new OrderQueryRequestForm();
            requestForm.setOutTradeNo(outTradeNo);
            requestForm.setNonceStr(WXPayUtil.generateNonceStr());
            Map<String, String> map = orderQuery(requestForm);
            return map;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public Map<String, String> orderQuery(OrderQueryRequestForm requestForm) {
        try {
            return wxPay.orderQuery(beanToMap(requestForm));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public Map<String, String> closeOrder(String outTradeNo) {
        try {
            CloseOrderRequestForm requestForm = new CloseOrderRequestForm();
            requestForm.setOutTradeNo(outTradeNo);
            requestForm.setNonceStr(WXPayUtil.generateNonceStr());
            return closeOrder(requestForm);
        } catch (Exception e) {
            WXPayUtil.getLogger().error("调用微信接口closeorder失败 ：{}", e.getMessage());
        }
        return null;
    }

    public Map<String, String> closeOrder(CloseOrderRequestForm requestForm) {
        try {
            return wxPay.closeOrder(beanToMap(requestForm));
        } catch (Exception e) {
            WXPayUtil.getLogger().error("调用微信接口closeorder失败 ：{}", e.getMessage());
        }
        return null;
    }

    public Map<String, String> refund(String outTradeNo, String outRefundNo, String totalFee, String refundFee) {
        try {
            RefundRequestForm requestForm = new RefundRequestForm();
            requestForm.setNonceStr(WXPayUtil.generateNonceStr());
            requestForm.setOutRefundNo(outRefundNo);
            requestForm.setOutTradeNo(outTradeNo);
            requestForm.setRefundFee(refundFee);
            requestForm.setTotalFee(totalFee);

            return refund(requestForm);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public Map<String, String> refund(RefundRequestForm requestForm) {
        try {
            return wxPay.refund(beanToMap(requestForm));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public Map<String, String> refundQuery(String outTradeNo) {
        try {
            RefundQueryRequestForm requestForm = new RefundQueryRequestForm();
            requestForm.setNonceStr(WXPayUtil.generateNonceStr());
            requestForm.setOutTradeNo(outTradeNo);
            return refundQuery(requestForm);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public Map<String, String> refundQuery(RefundQueryRequestForm requestForm) {
        try {
            return wxPay.refund(beanToMap(requestForm));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public Map<String, String> downloadBill(String billDate, String billType) {
        try {
            DownloadBillRequestForm requestForm = new DownloadBillRequestForm();
            requestForm.setBillDate(billDate);
            requestForm.setBillType(billType);
            requestForm.setNonceStr(WXPayUtil.generateNonceStr());
            return downloadBill(requestForm);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public Map<String, String> downloadBill(DownloadBillRequestForm requestForm) {
        try {
            return wxPay.downloadBill(beanToMap(requestForm));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public Map<String, String> downloadFundFlow(String billDate, String accountType) {
        try {
            DownloadFundFlowRequestForm requestForm = new DownloadFundFlowRequestForm();
            requestForm.setBillDate(billDate);
            requestForm.setAccountType(accountType);
            return downloadFundFlow(requestForm);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public Map<String, String> downloadFundFlow(DownloadFundFlowRequestForm requestForm) {
        try {
            return wxPay.downloadfundflow(beanToMap(requestForm));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public Map<String, String> payitilReport(String interfaceUrl, String executeTime, String returnCode,
                                             String returnMsg, String resultCode, String userIp) {
        try {
            ReportRequestForm requestForm = new ReportRequestForm();

            requestForm.setNonceStr(WXPayUtil.generateNonceStr());
            requestForm.setInterfaceUrl(interfaceUrl);
            requestForm.setExecuteTime(executeTime);
            requestForm.setReturnCode(returnCode);
            requestForm.setReturnMsg(returnMsg);
            requestForm.setResultCode(resultCode);
            requestForm.setUserIp(userIp);

            return payitilReport(requestForm);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public Map<String, String> payitilReport(ReportRequestForm requestForm) {
        try {
            return wxPay.report(beanToMap(requestForm));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public Map<String, String> batchQueryComment(String beginTime, String endTime, String offset) {
        try {
            BatchQueryCommentRequestForm requestForm = new BatchQueryCommentRequestForm();
            requestForm.setBeginTime(beginTime);
            requestForm.setEndTime(endTime);
            requestForm.setOffset("0");

            return batchQueryComment(requestForm);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public Map<String, String> batchQueryComment(BatchQueryCommentRequestForm requestForm) {
        try {
            return wxPay.batchQueryComment(beanToMap(requestForm));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 实体类转化为Map
     *
     * @param obj
     * @return
     */
    private Map<String, String> beanToMap(Object obj) {
        Map<String, Object> map = JSON.parseObject(JSON.toJSONString(obj));
        Map<String, String> resultMap = new HashMap<>();
        for (String key : map.keySet()) {
            resultMap.put(key, (String)map.get(key));
        }
        return resultMap;
    }

    /**
     * 随机生成订单号
     *
     * @return
     */
    public String generateRandomOrderNo() {
        int number = (int)((Math.random() * 9) * 1000);// 随机数
        String nowStr = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss"));
        return nowStr + number;
    }
}
