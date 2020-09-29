package cn.yccoding.order.controller;

import cn.yccoding.common.vo.R;
import cn.yccoding.order.feignapi.AliPayFeignApi;
import cn.yccoding.order.model.TradePaySummary;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Array;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.HashMap;

/**
 * 订单
 *
 * @author YC
 * @since 2020/9/26
 */
@Slf4j
@RestController
@RequestMapping("/order")
public class OrderController {

    @Autowired
    private AliPayFeignApi aliPayFeignApi;

    /**
     * 测试方法
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    public ResponseEntity<R> getOrderById(@PathVariable String id) {
        log.info("获取订单id=->{}", id);
        HashMap<String, Object> map = new HashMap<>();
        map.put("id", id);
        map.put("price", "100");
        map.put("date", LocalDateTime.now().toString());
        return R.ok().message("订单详情").data("item",map).buildResponseEntity();
    }

    /**
     * 支付宝支付
     * @return
     */
    @PostMapping("/ali-pay")
    public ResponseEntity<R> doOrder() {
        log.info("测试支付宝支付...");
        TradePaySummary summary = new TradePaySummary();
        summary.setSubject("测试支付宝当面付");
        summary.setOperatorId("yc");
        summary.setStoreId("yc");
        TradePaySummary.Item item = new TradePaySummary.Item();
        item.setGoodsId("1001");
        item.setGoodsName("测试商品");
        item.setPrice("1");
        item.setQuantity(1);
        summary.setItems(Arrays.asList(item));
        String qrcode = aliPayFeignApi.f2fPayTradePay(summary);
        if (StringUtils.isEmpty(qrcode)) {
            return R.error().message("网络繁忙").buildResponseEntity();
        }
        // TODO 保存订单，更新状态
        return R.ok().message("测试成功").data("qrcode",qrcode).buildResponseEntity();
    }
}
