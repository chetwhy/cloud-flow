# 单例模式

## 介绍

- 分类：创建型模式
- 目的：保证一个类全局只有一个实例对象
- 关键：构造器私有
- 场景：1. 全局频繁使用的对象；2. 重量级对象，不需要多个对象，数据库连接，线程池
- 优点：1. 将具体产品和创建者解耦；2. 符合单一职责原则；3. 扩展性高，如果想增加一个产品，只要扩展一个工厂类就可以
- 缺点：1. 没有接口，不能继承；2. 与单一职责冲突，一个类只关心内部逻辑，不关注外部如何实例化

## 源码应用

Spring & JDK 

 java.lang.Runtime org.springframework.aop.framework.ProxyFactoryBean org.springframework.beans.factory.support.DefaultSingletonBeanRegistry org.springframework.core.ReactiveAdapterRegistry

## Demo实现

### 实现方式

1. 创建一个英雄单例类，单例类构造器私有，
2. 提供获取静态实例的方法；

### 图例

![img](https://notes-yc-01.oss-cn-shenzhen.aliyuncs.com/blog/2020/11/16/a499881b181346eaaf69bff48d695c29.png)

### 实现方式

> 一下设计模式都考虑了线程安全

#### 饿汉模式

- 特点：类加载的初始化阶段就完成了实例的初始化。
- 设计：本质上就是借助于jvm 类加载机制，保证实例的唯一性（初始化过程只会执行一次）及线程安全（JVM以同步的形式来完成类加载的整个过程）
- 代码：`HungrySingleton.java`

#### 懒汉模式

- 特点：延迟加载， 只有在真正使用的时候，才开始实例化
- 设计：DCL双端校验锁优化，volatile关键字防止编译器（JIT）指令重排导致未初始化实例
- 代码：`LazySingleton.java`

#### 静态内部类

- 特点：懒加载的一种，只有在实际使用的时候，才会触发类的初始化
- 设计：本质上是利用类的加载机制来保证线程安全 
- 代码：`InnerClassSingleton.java`

#### 枚举

- 特点：天然不支持反射创建对应的实例，且有自己的反序列化机制
- 设计：本质上是利用类的加载机制来保证线程安全 
- 代码：`EnumSingleton.java`

> 反射攻击及序列化攻击课参考示例代码


