package cn.yccoding.gzh.service.impl;

import cn.yccoding.common.util.DateTimeUtils;
import cn.yccoding.common.util.XmlConvertUtils;
import cn.yccoding.gzh.constant.MsgType;
import cn.yccoding.gzh.domain.ReplyMessage;
import cn.yccoding.gzh.model.ImgReplyMsg;
import cn.yccoding.gzh.model.RcvCommonMsg;
import cn.yccoding.gzh.model.TextReplyMsg;
import cn.yccoding.gzh.model.VoiceReplyMsg;
import cn.yccoding.gzh.repository.ReplyMessageRepository;
import cn.yccoding.gzh.service.MessageService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.xml.bind.JAXBException;
import java.text.MessageFormat;
import java.util.Arrays;

/**
 * @Author YC
 * @create 2020/3/6
 */
@Service
@Slf4j
public class MessageServiceImpl implements MessageService {

    @Autowired
    private ReplyMessageRepository replyMessageRepository;

    @Override
    public String handleMessage(RcvCommonMsg rcvCommonMsg) {
        String toUser = rcvCommonMsg.getFromUserName();
        String fromUser = rcvCommonMsg.getToUserName();
        Long createTime = DateTimeUtils.getTimeStamp();
        String msgType = rcvCommonMsg.getMsgType();

        String replayMsg = null;
        try {
            switch (msgType) {
                case MsgType.TEXT:
                    replayMsg = handleTextMsg(toUser, fromUser, createTime, rcvCommonMsg.getContent());
                    break;
                case MsgType.IMAGE:
                    replayMsg = handleImageMsg(toUser, fromUser, createTime, rcvCommonMsg.getMediaId());
                    break;
                case MsgType.VOICE:
                    replayMsg = handleVoiceMsg(toUser, fromUser, createTime, rcvCommonMsg.getMediaId());
                    break;
                case MsgType.VIDEO:
                    replayMsg = handleVideoMsg();
                    break;
                case MsgType.SHORT_VIDEO:
                    replayMsg = handleShortVideoMsg();
                    break;
                case MsgType.LOCATION:
                    replayMsg = handleLocationMsg();
                    break;
                case MsgType.LINK:
                    replayMsg = handleLinkMsg();
                    break;
            }
        } catch (JAXBException e) {
            log.error("文本转换异常，接收:[{}]", rcvCommonMsg);
            String defaultBusy = replyMessageRepository.findByKeyword("default_busy").getText();
            return MessageFormat.format(defaultBusy, toUser, fromUser, createTime);
        }

        return replayMsg;
    }

    // 文本消息回复
    private String handleTextMsg(String toUser, String fromUser, Long createTime, String rcvContent)
        throws JAXBException {
        // 关键字回复，可使用properties或数据库
        ReplyMessage replyMessage = replyMessageRepository.findByKeyword(rcvContent);
        if (replyMessage != null && !replyMessage.getText().isEmpty()) {
            TextReplyMsg textReplyMsg = new TextReplyMsg().setToUserName(toUser).setFromUserName(fromUser)
                .setCreateTime(createTime).setContent(replyMessage.getText());
            return XmlConvertUtils.beanToXml(textReplyMsg, TextReplyMsg.class);
        }

        return null;
    }

    private String handleImageMsg(String toUser, String fromUser, Long createTime, String rcvMediaId)
        throws JAXBException {
        ImgReplyMsg imgReplyMsg = new ImgReplyMsg().setToUserName(toUser).setFromUserName(fromUser)
            .setCreateTime(createTime).setMediaId(Arrays.asList(rcvMediaId));
        return XmlConvertUtils.beanToXml(imgReplyMsg, ImgReplyMsg.class);
    }

    private String handleVoiceMsg(String toUser, String fromUser, Long createTime, String rcvMediaId)
        throws JAXBException {
        VoiceReplyMsg voiceReplyMsg = new VoiceReplyMsg().setToUserName(toUser).setFromUserName(fromUser)
            .setCreateTime(createTime).setMediaId(Arrays.asList(rcvMediaId));
        return XmlConvertUtils.beanToXml(voiceReplyMsg, VoiceReplyMsg.class);
    }

    // 待完善
    private String handleVideoMsg() {
        return null;
    }

    private String handleShortVideoMsg() {
        return null;
    }

    private String handleMusicMsg() {
        return null;
    }

    private String handleLocationMsg() {
        return null;
    }

    private String handleLinkMsg() {
        return null;
    }
}
