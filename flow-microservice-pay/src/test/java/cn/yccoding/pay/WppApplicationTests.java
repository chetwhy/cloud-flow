package cn.yccoding.pay;

import cn.yccoding.pay.util.ConstantPropertyUtils;
import cn.yccoding.pay.form.*;
import cn.yccoding.pay.sdk.PaymentConstants;
import cn.yccoding.pay.sdk.WXPayConfigImpl;
import cn.yccoding.pay.sdk.WXPayUtil;
import cn.yccoding.pay.service.PaymentService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Map;

@SpringBootTest
@RunWith(SpringRunner.class)
public class WppApplicationTests {

    @Autowired
    private PaymentService paymentService;

    @Test
    public void testUnifiedOrder() {
        String openid = "o4036jqo2PN9isV6N2FHGRsGRVqg";
        String ipAddr = "127.0.0.1";
        String url = "http://chety.mynatapp.cc";
        Map<String, Object> result1 =
                paymentService.unifiedorder(openid, PaymentConstants.TradeType.JSAPI.toString(), "1", "Test", ipAddr, url);
        UnifiedOrderRequestForm requestForm = new UnifiedOrderRequestForm();
        requestForm.setOutTradeNo(paymentService.generateRandomOrderNo());
        requestForm.setBody("Test");
        requestForm.setOpenid("o4036jqo2PN9isV6N2FHGRsGRVqg");
        requestForm.setSpbillCreateIp(ipAddr);
        requestForm.setTradeType(PaymentConstants.TradeType.JSAPI.toString());
        requestForm.setTotalFee("1");
        requestForm.setNotifyUrl("1");
        requestForm.setNonceStr(WXPayUtil.generateNonceStr());
        requestForm.setNotifyUrl(ConstantPropertyUtils.NOTIFY_URL);
        Map<String, Object> result2 = paymentService.unifiedorder(requestForm, url);
        System.out.println(result1);
        System.out.println(result2);
    }

    @Test
    public void testQuery() {
        Map<String, String> result1 = paymentService.orderQuery("201907051128063699");
        OrderQueryRequestForm requestForm = new OrderQueryRequestForm();
        requestForm.setOutTradeNo("201907051128063699");
        requestForm.setNonceStr(WXPayUtil.generateNonceStr());
        Map<String, String> result2 = paymentService.orderQuery(requestForm);
        System.out.println(result1);
        System.out.println(result2);
    }

    @Test
    public void testClose() {
        Map<String, String> result1 = paymentService.closeOrder("201907051128063699");
        CloseOrderRequestForm requestForm = new CloseOrderRequestForm();
        requestForm.setOutTradeNo("201907051128063699");
        requestForm.setNonceStr(WXPayUtil.generateNonceStr());
        Map<String, String> result2 = paymentService.closeOrder(requestForm);
        System.out.println(result1);
        System.out.println(result2);
    }

    @Test
    public void testRefund() {
        Map<String, String> result1 = paymentService.refund("201907051128063699", generateRandomOrderNo(), "1", "1");
        RefundRequestForm requestForm = new RefundRequestForm();
        requestForm.setOutTradeNo("201907051128063699");
        requestForm.setRefundFee("1");
        requestForm.setTotalFee("1");
        requestForm.setOutRefundNo(generateRandomOrderNo());
        requestForm.setNonceStr(WXPayUtil.generateNonceStr());
        Map<String, String> result2 = paymentService.refund(requestForm);
        System.out.println(result1);
        System.out.println(result2);
    }

    @Test
    public void testRefundQuery() {
        Map<String, String> result1 = paymentService.refundQuery("201907051128063699");
        RefundQueryRequestForm requestForm = new RefundQueryRequestForm();
        requestForm.setOutTradeNo("201907051128063699");
        requestForm.setNonceStr(WXPayUtil.generateNonceStr());
        Map<String, String> result2 = paymentService.refundQuery(requestForm);
        System.out.println(result1);
        System.out.println(result2);
    }

    @Test
    public void testDLBill() {
        Map<String, String> result1 = paymentService.downloadBill("20190705", "ALL");
        DownloadBillRequestForm requestForm = new DownloadBillRequestForm();
        requestForm.setBillDate("20190705");
        requestForm.setBillType(PaymentConstants.BillType.ALL.toString());
        requestForm.setNonceStr(WXPayUtil.generateNonceStr());
        Map<String, String> result2 = paymentService.downloadBill(requestForm);
        System.out.println(result1);
        System.out.println(result2);
    }

    @Test
    public void testDLFundFlow() {
        Map<String, String> result1 = paymentService.downloadFundFlow("20190705", "Basic");
        DownloadFundFlowRequestForm requestForm = new DownloadFundFlowRequestForm();
        requestForm.setBillDate("20190705");
        requestForm.setAccountType(PaymentConstants.AccountType.Basic.toString());
        requestForm.setNonceStr(WXPayUtil.generateNonceStr());
        Map<String, String> result2 = paymentService.downloadFundFlow(requestForm);
        System.out.println(result1);
        System.out.println(result2);
    }

    @Test
    public void testBatchQueryComment() {
        Map<String, String> result1 = paymentService.batchQueryComment("20190705000000", "20190706000000", "0");
        BatchQueryCommentRequestForm requestForm = new BatchQueryCommentRequestForm();
        requestForm.setBeginTime("20190705000000");
        requestForm.setEndTime("20190706000000");
        requestForm.setOffset("0");
        Map<String, String> result2 = paymentService.batchQueryComment(requestForm);
        System.out.println(result1);
        System.out.println(result2);
    }

    private String generateRandomOrderNo() {
        int number = (int)((Math.random() * 9) * 1000);// 随机数
        String nowStr = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmSSss"));
        return nowStr + number;
    }

}
