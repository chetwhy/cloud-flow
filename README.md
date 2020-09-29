# cloud-flow

云流——微服务聚合平台

## 核心依赖

SpringBoot 2.1.7.RELEASE

## 模块简介

```
cloud-flow
|-- flow-framework-parent 父项目，管理版本依赖
    |-- flow-framework-common           公共模块，为各个子模块调用
    |-- flow-framework-mbg              mybatis-plus自动生成工具
    |-- flow-microservice-authcenter    授权中心
    |-- flow-microservice-gatewayr      网关
    |-- flow-microservice-member        会员中心
    |-- flow-microservice-order         订单服务
    |-- flow-microservice-payment       支付服务
    |-- flow-microservice-product       产品服务
```

## 启动说明

### 注册&配置中心

项目使用的微服务是基于alibaba的springcloud,因此需要做以下准备：

- 下载[nacos](https://github.com/alibaba/nacos/releases)，他是服务的注册中心和配置中心。详细使用可参考[官方文档](https://nacos.io/zh-cn/docs/what-is-nacos.html)

![](https://notes-yc-01.oss-cn-shenzhen.aliyuncs.com/blog/2020/09/30/992c5d10be444f4a967b4e57f419eb44.png)

- 解压到本地，进入bin目录，打开终端

- 输入命令，启动nacos，以单机为例：

```shell script
sh startup.sh -m standalone
```

- 访问nacos界面，默认端口8848 http://localhost:8848/nacos/index.html

![](https://notes-yc-01.oss-cn-shenzhen.aliyuncs.com/blog/2020/09/30/e7adadbbba7d4b2ab7dd34406c45a5b2.png)

### 服务启动

以下启动流程是一个常规的微服务启动流程，若是开发或测试功能，根据需要启动即可

1. GateWayApplication

网关服务，支持路由，断言，过滤等功能

2. AuthCenterApplication

授权中心，支持token的颁发

> 为避免只启动单独几个应用，默认注释认证和授权配置

3. ProductApplication

产品服务，模拟生产者

4. OderApplication

订单服务，默认消费者

5. PaymentApplication

支付服务，支持支付宝和微信支付

6. MemberApplication

会员中心，简要集成session和jwt的使用

## 代码格式化说明
本项目代码使用了阿里巴巴的代码规范，Eeclipse/Idea代码格式化设置可参考链接: [java代码格式化模板（阿里代码规范）](http://ddrv.cn/a/235259)
