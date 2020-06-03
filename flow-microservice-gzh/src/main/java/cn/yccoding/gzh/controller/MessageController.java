package cn.yccoding.gzh.controller;

import cn.yccoding.common.util.XmlConvertUtils;
import cn.yccoding.gzh.model.RcvCommonMsg;
import cn.yccoding.gzh.service.MessageService;
import cn.yccoding.gzh.util.SignatureUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.xml.bind.JAXBException;

/**
 * @Author YC
 * @create 2020/3/3
 * 消息处理控制器
 */
@RestController
@RequestMapping("/api/v1/msg")
@Slf4j
public class MessageController {

    @Autowired
    private MessageService messageService;

    /**
     * 微信公众号url接入确认
     * @param signature
     * @param timestamp
     * @param nonce
     * @param echostr
     * @return
     */
    @GetMapping("/portal")
    public String validateAccess(String signature, String timestamp, String nonce, String echostr) {
        log.info("开始测试接入微信...");
        if (!SignatureUtils.checkSignature(signature, timestamp, nonce)) {
            log.error("公众号接入失败");
            return null;
        }
        log.info("公众号接入成功,echostr:[{}]", echostr);
        return echostr;
    }

    @PostMapping("/portal")
    public String handleMessage(@RequestBody RcvCommonMsg rcvCommonMsg) throws JAXBException {
        String replyMsg = messageService.handleMessage(rcvCommonMsg);
        // TODO 暂不支持直接bean注解输出
        String rcvMsg = XmlConvertUtils.beanToXml(rcvCommonMsg, RcvCommonMsg.class);
        log.info("公众号接收消息:[{}}, 回复消息:[{}]",rcvMsg,replyMsg);
        return replyMsg;
    }

}
