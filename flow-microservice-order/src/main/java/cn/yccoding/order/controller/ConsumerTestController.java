package cn.yccoding.order.controller;

import cn.yccoding.common.vo.R;
import cn.yccoding.order.feignapi.ProducerTestFeignApi;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * 消费者测试
 *
 * @author YC
 * @since 2020/9/23
 */
@Slf4j
@RestController
@RequestMapping("/consumer")
public class ConsumerTestController {
    @Autowired
    ProducerTestFeignApi producerTestFeignApi;

    @GetMapping("/local")
    public ResponseEntity<R> consume() {
        log.info("消费者调用...");
        return R.ok().message("消费者调用成功").buildResponseEntity();
    }

    //@GlobalTransactional
    @GetMapping("/feign")
    public ResponseEntity<R> feign() {
        log.info("消费者远程调用...");
        R r = producerTestFeignApi.produce().getBody();
        if (!r.isSuccess()) {
            return R.error().message("生产者繁忙...").buildResponseEntity();
        }
        return R.ok().message("远程调用成功").data(r.getData()).buildResponseEntity();
    }

}
