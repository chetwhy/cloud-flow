package cn.yccoding.gzh.controller;

import cn.yccoding.common.util.XmlConvertUtil;
import cn.yccoding.gzh.model.RcvCommonMsg;
import cn.yccoding.gzh.model.TextReplyMsg;
import cn.yccoding.gzh.service.MessageService;
import cn.yccoding.gzh.util.OfficialAccountUtil;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
    private static final Logger logger = LoggerFactory.getLogger(MessageController.class);

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
        logger.info("开始测试接入微信...");
        if (!OfficialAccountUtil.checkSignature(signature, timestamp, nonce)) {
            logger.error("公众号接入失败");
            return null;
        }
        logger.info("公众号接入成功,echostr:[{}]", echostr);
        return echostr;
    }

    @PostMapping("/portal")
    public Object handleMessage(@RequestBody RcvCommonMsg rcvCommonMsg) throws JAXBException {
        TextReplyMsg replyMsg = messageService.handleMessage(rcvCommonMsg);
        // TODO 暂不支持直接bean注解输出
        return XmlConvertUtil.beanToXml(replyMsg, TextReplyMsg.class);
    }

}
