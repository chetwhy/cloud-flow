package cn.yccoding.order.feignapi.fallback;

import cn.yccoding.common.base.ResultCodeEnum;
import cn.yccoding.common.exception.CustomException;
import cn.yccoding.order.feignapi.AliPayFeignApi;
import cn.yccoding.order.model.TradePaySummary;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * ali
 *
 * @author YC
 * @since 2020/9/29
 */
@Slf4j
@Component
public class AliPayFeignApiFallBack implements AliPayFeignApi {

    @Override
    public String f2fPayTradePay(@RequestBody TradePaySummary summary) {
        log.info("当面付请求异常...");
        // TODO 待完善逻辑
        return null;
    }
}
