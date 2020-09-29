package cn.yccoding.product.controller;

import cn.yccoding.common.vo.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 生产者测试
 *
 * @author YC
 * @since 2020/9/23
 */
@Slf4j
@RestController
public class ProducerTestController {

    @GetMapping("/producer")
    public ResponseEntity<R> produce() {
        log.info("生产者调用...");
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        int i = 1/0;
        return R.ok().message("生产者调用成功...").data("item","from producer").buildResponseEntity();
    }
}
