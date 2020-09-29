package cn.yccoding.order.feignapi;

import cn.yccoding.common.vo.R;
import cn.yccoding.order.feignapi.fallback.ProducerTestFeignApiFallback;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * 远程调用
 *
 * @author YC
 * @since 2020/9/23
 */
@FeignClient(name = "flow-product", fallback = ProducerTestFeignApiFallback.class)
public interface ProducerTestFeignApi {

    @GetMapping("/producer")
    ResponseEntity<R> produce();
}
