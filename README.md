# cloud-flow

云流——微服务聚合平台

## 核心依赖

SpringBoot 2.0.7.RELEASE

## 模块简介

```xml
cloud-flow
|—— flow-framework-parent 父项目，管理版本依赖
    |—— flow-framework-common   公共模块，为各个子模块调用
    |—— flow-microservice-gzh   微信公众号模块，主要管理微信公众号消息处理
    |—— flow-microservice-blob  blob数据库模块，主要实现文件上传到azure blob
```

## 启动说明

1. **GzhApplication**：可直接启动。若携带加密参数启动，可使用JasyptUtil.encyptPwd(..)，本模块默认对AppSecret加密，启动脚本如：`--spring.profiles.active=local --jasypt.encryptor.password=123456`
