package cn.yccoding.pay;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Map;

import cn.yccoding.pay.sdk.WXPayConfigYMLImpl;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest
@RunWith(SpringRunner.class)
public class WppApplicationTests {

    @Autowired
    private WXPayConfigYMLImpl wxPayConfigImpl;

    @Autowired
    private WCPBackendUtil wcpBackendUtil;

    @Test
    public void testUnifiedOrder() {
        String openid = "o4036jqo2PN9isV6N2FHGRsGRVqg";
        String ipAddr = "127.0.0.1";
        String url = "http://chety.mynatapp.cc";
        Map<String, Object> result1 =
            wcpBackendUtil.unifiedorder(openid, WCPBackendConst.TradeType.JSAPI.toString(), "1", "Test", ipAddr, url);
        UnifiedOrderRequestEntity requestEntity = new UnifiedOrderRequestEntity();
        requestEntity.setOutTradeNo(wcpBackendUtil.generateRandomOrderNo());
        requestEntity.setBody("Test");
        requestEntity.setOpenid("o4036jqo2PN9isV6N2FHGRsGRVqg");
        requestEntity.setSpbillCreateIp(ipAddr);
        requestEntity.setTradeType(WCPBackendConst.TradeType.JSAPI.toString());
        requestEntity.setTotalFee("1");
        requestEntity.setNotifyUrl("1");
        requestEntity.setNonceStr(WXPayUtil.generateNonceStr());
        requestEntity.setNotifyUrl(wcpConfigParams.getNotifyUrl());
        Map<String, Object> result2 = wcpBackendUtil.unifiedorder(requestEntity, url);
        System.out.println(result1);
        System.out.println(result2);
    }

    @Test
    public void testQuery() {
        Map<String, String> result1 = wcpBackendUtil.orderquery("201907051128063699");
        OrderQueryRequestEntity requestEntity = new OrderQueryRequestEntity();
        requestEntity.setOutTradeNo("201907051128063699");
        requestEntity.setNonceStr(WXPayUtil.generateNonceStr());
        Map<String, String> result2 = wcpBackendUtil.orderquery(requestEntity);
        System.out.println(result1);
        System.out.println(result2);
    }

    @Test
    public void testClose() {
        Map<String, String> result1 = wcpBackendUtil.closeorder("201907051128063699");
        CloseOrderRequestEntity requestEntity = new CloseOrderRequestEntity();
        requestEntity.setOutTradeNo("201907051128063699");
        requestEntity.setNonceStr(WXPayUtil.generateNonceStr());
        Map<String, String> result2 = wcpBackendUtil.closeorder(requestEntity);
        System.out.println(result1);
        System.out.println(result2);
    }

    @Test
    public void testRefund() {
        Map<String, String> result1 = wcpBackendUtil.refund("201907051128063699", generateRandomOrderNo(), "1", "1");
        RefundRequestEntity requestEntity = new RefundRequestEntity();
        requestEntity.setOutTradeNo("201907051128063699");
        requestEntity.setRefundFee("1");
        requestEntity.setTotalFee("1");
        requestEntity.setOutRefundNo(generateRandomOrderNo());
        requestEntity.setNonceStr(WXPayUtil.generateNonceStr());
        Map<String, String> result2 = wcpBackendUtil.refund(requestEntity);
        System.out.println(result1);
        System.out.println(result2);
    }

    @Test
    public void testRefundQuery() {
        Map<String, String> result1 = wcpBackendUtil.refundquery("201907051128063699");
        RefundQueryRequestEntity requestEntity = new RefundQueryRequestEntity();
        requestEntity.setOutTradeNo("201907051128063699");
        requestEntity.setNonceStr(WXPayUtil.generateNonceStr());
        Map<String, String> result2 = wcpBackendUtil.refundquery(requestEntity);
        System.out.println(result1);
        System.out.println(result2);
    }

    @Test
    public void testDLBill() {
        Map<String, String> result1 = wcpBackendUtil.downloadbill("20190705", "ALL");
        DownloadBillRequestEntity requestEntity = new DownloadBillRequestEntity();
        requestEntity.setBillDate("20190705");
        requestEntity.setBillType(WCPBackendConst.BillType.ALL.toString());
        requestEntity.setNonceStr(WXPayUtil.generateNonceStr());
        Map<String, String> result2 = wcpBackendUtil.downloadbill(requestEntity);
        System.out.println(result1);
        System.out.println(result2);
    }

    @Test
    public void testDLFundFlow() {
        Map<String, String> result1 = wcpBackendUtil.downloadfundflow("20190705", "Basic");
        DownloadFundFlowRequestEntity requestEntity = new DownloadFundFlowRequestEntity();
        requestEntity.setBillDate("20190705");
        requestEntity.setAccountType(WCPBackendConst.AccountType.Basic.toString());
        requestEntity.setNonceStr(WXPayUtil.generateNonceStr());
        Map<String, String> result2 = wcpBackendUtil.downloadfundflow(requestEntity);
        System.out.println(result1);
        System.out.println(result2);
    }

    @Test
    public void testBatchQueryComment() {
        Map<String, String> result1 = wcpBackendUtil.batchquerycomment("20190705000000", "20190706000000", "0");
        BatchQueryCommentRequestEntity requestEntity = new BatchQueryCommentRequestEntity();
        requestEntity.setBeginTime("20190705000000");
        requestEntity.setEndTime("20190706000000");
        requestEntity.setOffset("0");
        Map<String, String> result2 = wcpBackendUtil.batchquerycomment(requestEntity);
        System.out.println(result1);
        System.out.println(result2);
    }

    private String generateRandomOrderNo() {
        int number = (int)((Math.random() * 9) * 1000);// 随机数
        String nowStr = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmSSss"));
        return nowStr + number;
    }

}
