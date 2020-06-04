# cloud-flow

云流——微服务聚合平台

## 核心依赖

SpringBoot 2.0.7.RELEASE

## 模块简介

```
cloud-flow
|—— flow-framework-parent 父项目，管理版本依赖
    |—— flow-framework-common       公共模块，为各个子模块调用
    |—— flow-microservice-gzh       微信公众号模块，主要管理微信公众号消息处理
    |—— flow-microservice-blob      blob数据库模块，主要实现文件上传到azure blob
    |—— flow-microservice-mail      邮件发送功能，rabbitmq监听
    |—— flow-microservice-manage    后台管理模块
    |—— flow-microservice-pay       微信支付模块
```

## 启动说明

1. **GzhApplication**：
- 可直接启动。若携带加密参数启动，可使用JasyptUtil.encyptPwd(..)，本模块默认对AppSecret加密，启动脚本如：`--spring.profiles.active=dev --jasypt.encryptor.password=123456`; 
- 默认使用远程redis，如果没有redis可以修改配置local.active=ture

2. **BlobApplication**：
- 需开通blob数据库。数据库参数在yml修改

3. **ManageApplication**

4. **MailApplication**
- 需配置邮箱服务和授权码

## 代码格式化说明
本项目代码使用了阿里巴巴的代码规范，Eeclipse/Idea代码格式化设置可参考链接: [java代码格式化模板（阿里代码规范）](http://ddrv.cn/a/235259)
