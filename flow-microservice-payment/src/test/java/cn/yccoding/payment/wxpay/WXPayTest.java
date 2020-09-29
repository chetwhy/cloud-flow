package cn.yccoding.payment.wxpay;

import cn.yccoding.payment.util.PaymentUtils;
import cn.yccoding.payment.wxpay.client.TokenClient;
import cn.yccoding.payment.wxpay.service.WXPayTradeService;
import com.alibaba.fastjson.JSON;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Map;

/**
 * 微信支付测试
 *
 * @author YC
 * @since 2020/9/28
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class WXPayTest {
    @Autowired
    private TokenClient tokenClient;

    @Autowired
    private WXPayTradeService WXPayTradeService;

    @Test
    public void testGetToken() {
        String accessToken = tokenClient.getAccessToken();
        System.out.println(accessToken);
    }

    @Test
    public void testUnifiedOrder() {
        String openid = "o4036jqo2PN9isV6N2FHGRsGRVqg";
        String ipAddr = "127.0.0.1";
        String url = "http://chety.mynatapp.cc";
        Map<String, Object> result =
                WXPayTradeService.unifiedorder(openid, "JSAPI", "1", "Test", ipAddr, url);
        System.out.println(JSON.toJSONString(result));
    }

    @Test
    public void testQuery() {
        Map<String, String> result = WXPayTradeService.orderQuery("201907051128063699");
        System.out.println(JSON.toJSONString(result));
    }

    @Test
    public void testClose() {
        Map<String, String> result = WXPayTradeService.closeOrder("201907051128063699");
        System.out.println(JSON.toJSONString(result));
    }

    @Test
    public void testRefund() {
        Map<String, String> result = WXPayTradeService.refund("201907051128063699", PaymentUtils.generateRandomOrderNo(), "1", "1");
        System.out.println(JSON.toJSONString(result));
    }

    @Test
    public void testRefundQuery() {
        Map<String, String> result = WXPayTradeService.refundQuery("201907051128063699");
        System.out.println(JSON.toJSONString(result));
    }

    @Test
    public void testDLBill() {
        Map<String, String> result = WXPayTradeService.downloadBill("20190705", "ALL");
        System.out.println(JSON.toJSONString(result));
    }

    @Test
    public void testDLFundFlow() {
        Map<String, String> result = WXPayTradeService.downloadFundFlow("20190705", "Basic");
        System.out.println(JSON.toJSONString(result));
    }

    @Test
    public void testBatchQueryComment() {
        Map<String, String> result = WXPayTradeService.batchQueryComment("20190705000000", "20190706000000", "0");
        System.out.println(JSON.toJSONString(result));
    }
}
