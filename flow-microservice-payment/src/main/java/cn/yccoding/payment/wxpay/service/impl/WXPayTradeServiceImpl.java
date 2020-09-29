package cn.yccoding.payment.wxpay.service.impl;

import cn.hutool.core.map.MapUtil;
import cn.yccoding.common.base.ResultCodeEnum;
import cn.yccoding.common.exception.CustomException;
import cn.yccoding.payment.util.PaymentUtils;
import cn.yccoding.payment.wxpay.client.TokenClient;
import cn.yccoding.payment.wxpay.model.*;
import cn.yccoding.payment.wxpay.sdk.WXPay;
import cn.yccoding.payment.wxpay.service.WXPayTradeService;
import cn.yccoding.payment.wxpay.util.SignatureUtils;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.reflect.Type;
import java.math.BigDecimal;
import java.util.Map;
import java.util.stream.Collectors;

import static cn.yccoding.payment.config.WXPaymentConfig.*;
import static cn.yccoding.payment.wxpay.sdk.WXPayConstants.FAIL;
import static cn.yccoding.payment.wxpay.sdk.WXPayConstants.SUCCESS;

/**
 * 支付服务
 *
 * @author YC
 * @since 2020/9/27
 */
@Slf4j
@Service
public class WXPayTradeServiceImpl implements WXPayTradeService {

    @Autowired
    private TokenClient tokenClient;
    @Autowired
    private WXPay wxPay;

    @Override
    public Map<String, Object> unifiedorder(String openid, String tradeType, String price, String productDesc,
                                            String terminalIP, String requestUrl) {
        UnifiedOrder requestForm = new UnifiedOrder();
        requestForm.setBody(productDesc);
        requestForm.setOutTradeNo(PaymentUtils.generateRandomOrderNo());
        // 汇率，元转为分
        BigDecimal priceBig = new BigDecimal(price);
        String priceFen = priceBig.multiply(BigDecimal.valueOf(100L)).toString();
        requestForm.setTotalFee(priceFen);
        requestForm.setSpbillCreateIp(terminalIP);
        requestForm.setOpenid(openid);
        requestForm.setTradeType(tradeType);
        requestForm.setNotifyUrl(NOTIFY_URL);

        // 利用sdk统一下单,已自动调用 wxpay.fillRequestData (data);
        Map<String, String> reqMap = objectToMap(requestForm);
        Map<String, String> respMap = null;
        try {
            respMap = wxPay.unifiedOrder(reqMap);
        } catch (Exception e) {
            log.error("微信支付接口调用失败->{}", e.getMessage());
            throw new CustomException(ResultCodeEnum.WXPAY_REQUEST_FAILED);
        }
        log.info("微信支付下单结果->{}", respMap);

        // 统一下单接口调用成功
        String returnCode = respMap.get("return_code");
        String resultCode = respMap.get("result_code");
        String nonceStr = respMap.get("nonce_str");
        // return_code 和result_code都为SUCCESS
        if (returnCode.equals(SUCCESS) && resultCode.equals((SUCCESS))) {
            String prepayId = respMap.get("prepay_id");
            String accessToken = tokenClient.getAccessToken(APP_ID, APP_SECRET);
            String jsApiTicket = tokenClient.getJsApiTicket(accessToken);
            Map<String, Object> map = null;
            try {
                map = SignatureUtils.permissionValidate(APP_ID, nonceStr, requestUrl, prepayId, API_KEY, jsApiTicket);
            } catch (Exception e) {
                log.warn("支付结果签名校验异常");
            }
            return map;
        }
        // return_code为SUCCESS,result_code为false
        if (returnCode.equals(SUCCESS)) {
            return respMap.entrySet().stream().collect(Collectors.toMap(Map.Entry::getKey, v -> (Object) v.getValue()));
        }
        // 微信支付异常，或return_code 为fail
        return MapUtil.builder("return_code", (Object) FAIL).build();
    }

    @Override
    public Map<String, String> orderQuery(String outTradeNo) {
        OrderQuery requestForm = new OrderQuery();
        requestForm.setOutTradeNo(outTradeNo);
        Map<String, String> reqMap = objectToMap(requestForm);
        try {
            return wxPay.orderQuery(reqMap);
        } catch (Exception e) {
            log.error("微信支付接口调用失败->{}", e.getMessage());
            throw new CustomException(ResultCodeEnum.WXPAY_REQUEST_FAILED);
        }
    }

    @Override
    public Map<String, String> closeOrder(String outTradeNo) {
        CloseOrder requestForm = new CloseOrder();
        requestForm.setOutTradeNo(outTradeNo);

        Map<String, String> reqMap = objectToMap(requestForm);
        try {
            return wxPay.closeOrder(reqMap);
        } catch (Exception e) {
            log.error("微信支付接口调用失败->{}", e.getMessage());
            throw new CustomException(ResultCodeEnum.WXPAY_REQUEST_FAILED);
        }
    }

    @Override
    public Map<String, String> refund(String outTradeNo, String outRefundNo, String totalFee, String refundFee) {
        Refund requestForm = new Refund();

        requestForm.setOutRefundNo(outRefundNo);
        requestForm.setOutTradeNo(outTradeNo);
        requestForm.setRefundFee(refundFee);
        requestForm.setTotalFee(totalFee);
        Map<String, String> reqMap = objectToMap(requestForm);
        try {
            return wxPay.refund(reqMap);
        } catch (Exception e) {
            log.error("微信支付接口调用失败->{}", e.getMessage());
            throw new CustomException(ResultCodeEnum.WXPAY_REQUEST_FAILED);
        }
    }

    @Override
    public Map<String, String> refundQuery(String outTradeNo) {
        RefundQuery requestForm = new RefundQuery();

        requestForm.setOutTradeNo(outTradeNo);
        Map<String, String> reqMap = objectToMap(requestForm);
        try {
            return wxPay.refundQuery(reqMap);
        } catch (Exception e) {
            log.error("微信支付接口调用失败->{}", e.getMessage());
            throw new CustomException(ResultCodeEnum.WXPAY_REQUEST_FAILED);
        }
    }

    @Override
    public Map<String, String> downloadBill(String billDate, String billType) {
        DownloadBill requestForm = new DownloadBill();
        requestForm.setBillDate(billDate);
        requestForm.setBillType(billType);

        Map<String, String> reqMap = objectToMap(requestForm);
        try {
            return wxPay.downloadBill(reqMap);
        } catch (Exception e) {
            log.error("微信支付接口调用失败->{}", e.getMessage());
            throw new CustomException(ResultCodeEnum.WXPAY_REQUEST_FAILED);
        }
    }

    @Override
    public Map<String, String> downloadFundFlow(String billDate, String accountType) {
        DownloadFundFlow requestForm = new DownloadFundFlow();
        requestForm.setBillDate(billDate);
        requestForm.setAccountType(accountType);
        Map<String, String> reqMap = objectToMap(requestForm);
        try {
            return wxPay.downloadfundflow(reqMap);
        } catch (Exception e) {
            log.error("微信支付接口调用失败->{}", e.getMessage());
            throw new CustomException(ResultCodeEnum.WXPAY_REQUEST_FAILED);
        }
    }

    @Override
    public Map<String, String> report(String interfaceUrl, String executeTime, String returnCode,
                                      String returnMsg, String resultCode, String userIp) {
        Report requestForm = new Report();


        requestForm.setInterfaceUrl(interfaceUrl);
        requestForm.setExecuteTime(executeTime);
        requestForm.setReturnCode(returnCode);
        requestForm.setReturnMsg(returnMsg);
        requestForm.setResultCode(resultCode);
        requestForm.setUserIp(userIp);
        Map<String, String> reqMap = objectToMap(requestForm);
        try {
            return wxPay.report(reqMap);
        } catch (Exception e) {
            log.error("微信支付接口调用失败->{}", e.getMessage());
            throw new CustomException(ResultCodeEnum.WXPAY_REQUEST_FAILED);
        }
    }

    @Override
    public Map<String, String> batchQueryComment(String beginTime, String endTime, String offset) {
        BatchQueryComment requestForm = new BatchQueryComment();
        requestForm.setBeginTime(beginTime);
        requestForm.setEndTime(endTime);
        requestForm.setOffset("0");
        Map<String, String> reqMap = objectToMap(requestForm);
        try {
            Map<String, String> map = wxPay.batchQueryComment(reqMap);
            return map;
        } catch (Exception e) {
            log.error("微信支付接口调用失败->{}", e.getMessage());
            throw new CustomException(ResultCodeEnum.WXPAY_REQUEST_FAILED);
        }
    }

    /**
     * java对象转为指定map
     *
     * @param obj
     * @return
     */
    private Map<String, String> objectToMap(Object obj) {
        // 去除空值，转化为map
        Type mapType = new TypeReference<Map<String, String>>() {
        }.getType();
        return JSON.parseObject(JSON.toJSONString(obj), mapType);
    }
}
