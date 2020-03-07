package cn.yccoding.gzh.service;

import cn.yccoding.gzh.model.RcvCommonMsg;

/**
 * @Author YC
 * @create 2020/3/6
 * 微信消息处理服务
 */
public interface MessageService {
    String handleMessage(RcvCommonMsg rcvCommonMsg);
}
