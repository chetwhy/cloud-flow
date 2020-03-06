package cn.yccoding.gzh.service;

import cn.yccoding.gzh.model.RcvCommonMsg;
import cn.yccoding.gzh.model.TextReplyMsg;

/**
 * @Author YC
 * @create 2020/3/6
 * 微信消息处理服务
 */
public interface MessageService {
    TextReplyMsg handleMessage(RcvCommonMsg rcvCommonMsg);
}
