package cn.yccoding.order.feignapi;

import cn.yccoding.order.feignapi.fallback.AliPayFeignApiFallBack;
import cn.yccoding.order.model.TradePaySummary;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * ali
 *
 * @author YC
 * @since 2020/9/29
 */
@FeignClient(name = "flow-payment",fallback = AliPayFeignApiFallBack.class)
public interface AliPayFeignApi {

    /**
     * 当面付
     * @param summary
     * @return
     */
    @PostMapping("/ali-pay/f2f")
    String f2fPayTradePay(@RequestBody TradePaySummary summary);
}
