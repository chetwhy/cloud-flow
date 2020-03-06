package cn.yccoding.gzh.service.impl;

import org.springframework.stereotype.Service;

import cn.yccoding.common.util.DateTimeUtil;
import cn.yccoding.gzh.constant.MsgType;
import cn.yccoding.gzh.model.RcvCommonMsg;
import cn.yccoding.gzh.model.TextReplyMsg;
import cn.yccoding.gzh.service.MessageService;

/**
 * @Author YC
 * @create 2020/3/6
 */
@Service
public class MessageServiceImpl implements MessageService {
    @Override
    public TextReplyMsg handleMessage(RcvCommonMsg rcvCommonMsg) {
        String toUser = rcvCommonMsg.getFromUserName();
        String fromUser = rcvCommonMsg.getToUserName();
        Long createTime = DateTimeUtil.getTimeStamp();
        String msgType = rcvCommonMsg.getMsgType();

        switch (msgType) {
            case MsgType.TEXT:
                TextReplyMsg textReplyMsg = handleTextMsg(toUser, fromUser, rcvCommonMsg.getContent(), DateTimeUtil.getTimeStamp());
                return textReplyMsg;
        }

        return null;
    }

    private TextReplyMsg handleTextMsg(String toUser, String fromUser, String rcvContent, Long createTime) {
        String replyContent = rcvContent;
        // 关键字回复，可使用properties或数据库
        if (rcvContent.contains("你好")) {
            replyContent = "你好，欢迎关注，这是一条测试回复";
        } else if (rcvContent.contains("hello")) {
            replyContent = "hello, thanks visit, it's a test response";
        }
        TextReplyMsg textReplyMsg = new TextReplyMsg().setToUserName(toUser).setFromUserName(fromUser)
            .setCreateTime(createTime).setContent(replyContent);
        return textReplyMsg;
    }
}
