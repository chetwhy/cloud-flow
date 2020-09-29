package cn.yccoding.payment.controller;

import cn.yccoding.common.base.ResultCodeEnum;
import cn.yccoding.common.exception.CustomException;
import cn.yccoding.payment.alipay.model.TradePaySummary;
import cn.yccoding.payment.alipay.sdk.config.Configs;
import cn.yccoding.payment.alipay.sdk.model.result.AlipayF2FQueryResult;
import cn.yccoding.payment.alipay.service.TradeService;
import com.alipay.api.AlipayApiException;
import com.alipay.api.internal.util.AlipaySignature;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Enumeration;
import java.util.Map;
import java.util.TreeMap;

/**
 * 阿里支付
 *
 * @author YC
 * @since 2020/9/29
 */
@Slf4j
@RestController
@RequestMapping("/ali-pay/f2f")
public class AliPayF2FController {
    @Autowired
    private TradeService tradeService;

    @PostMapping
    public String f2fPayTradePay(@RequestBody TradePaySummary summary) {
        log.info("测试支付宝支付...");
        return tradeService.f2fPayTradePay(summary);
    }

    @GetMapping
    public AlipayF2FQueryResult f2fPayTradeOrder(@RequestParam String outTradeNo) {
        log.info("测试支付宝查询...");
        return tradeService.f2fTradeQuery(outTradeNo);
    }

    /**
     * 支付宝支付回调,根据文档:
     * 1.支付宝是用 POST 方式发送通知信息
     * 2.在通知返回参数列表中，除去sign、sign_type两个参数外，凡是通知返回回来的参数皆是待验签的参数。将剩下参数进行 url_decode, 然后进行字典排序，组成字符串，得到待签名字符串
     * 3.程序执行完后必须通过 PrintWriter 类打印输出"success"（不包含引号）。如果商户反馈给支付宝的字符不是 success 这7个字符，支付宝服务器会不断重发通知，直到超过 24 小时 22 分钟。一般情况下，25 小时以内完成 8 次通知（通知的间隔频率一般是：4m,10m,10m,1h,2h,6h,15h）
     *
     * @return
     */
    @PostMapping("/callback")
    public void aliPayCallback(HttpServletRequest request, HttpServletResponse response) {
        log.info("接受支付宝回调");
        Enumeration<String> names = request.getParameterNames();
        Map<String, String> map = new TreeMap<>();
        while (names.hasMoreElements()) {
            String name = names.nextElement();
            String value = request.getParameter(name);
            log.info("parameter name->{},value->{}", name, value);
            if (!"sign_type".equalsIgnoreCase(name)) {
                map.put(name, value);
            }
        }
        // 异步返回结果验签
        try {
            boolean checkFlag = AlipaySignature.rsaCheckV2(map, Configs.getAlipayPublicKey(), "utf-8", Configs.getSignType());
            PrintWriter writer = response.getWriter();
            if (checkFlag) {
                writer.print("success");
            } else {
                writer.print("unSuccess");
            }
        } catch (AlipayApiException | IOException e) {
            throw new CustomException(ResultCodeEnum.ALIPAY_CALLBACK_CHECK_FAILED);
        }
    }
}
