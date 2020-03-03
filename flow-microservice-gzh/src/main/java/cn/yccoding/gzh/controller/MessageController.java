package cn.yccoding.gzh.controller;

import cn.yccoding.common.exception.CustomException;
import cn.yccoding.common.vo.R;
import cn.yccoding.gzh.util.OfficialAccountUtil;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author YC
 * @create 2020/3/3
 * 消息处理控制器
 */
@RestController
@RequestMapping("/api/v1/msg")
@Slf4j
public class MessageController {
    private static final Logger logger = LoggerFactory.getLogger(MessageController.class);

    /**
     * 微信公众号url接入确认
     * @param signature
     * @param timestamp
     * @param nonce
     * @param echostr
     * @return
     */
    @GetMapping("/portal")
    @ResponseBody
    public String validateAccess(String signature, String timestamp, String nonce, String echostr) {
        if (!OfficialAccountUtil.checkSignature(signature, timestamp, nonce)) {
            logger.error("公众号接入失败");
            return null;
        }
        logger.info("公众号接入成功,echostr:[{}]", echostr);
        return echostr;
    }

    @GetMapping("/hello")
    public R testResult() {
        System.out.println("00000000000");
        logger.info("come in...");
        if (1 == 1) {
            throw new CustomException(100, "test case...");
        }
        return R.ok().data("k1","v1");
    }
}
