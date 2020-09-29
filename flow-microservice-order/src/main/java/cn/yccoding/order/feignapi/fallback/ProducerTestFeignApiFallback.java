package cn.yccoding.order.feignapi.fallback;

import cn.yccoding.common.vo.R;
import cn.yccoding.order.feignapi.ProducerTestFeignApi;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

/**
 * 降级
 *
 * @author YC
 * @since 2020/9/24
 */
@Slf4j
@Component
public class ProducerTestFeignApiFallback implements ProducerTestFeignApi {

    @Override
    public ResponseEntity<R> produce() {
        log.error("服务异常...");
        return R.error().message("生产者忙碌...").buildResponseEntity();
    }
}
