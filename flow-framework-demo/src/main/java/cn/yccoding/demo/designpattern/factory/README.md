# 工厂模式

## 介绍

- 分类：创建型模式
- 目的：定义一个创建对象的接口，让子类决定实例化哪一个工厂类，使类的创建过程延迟到子类进行
- 解决：让子类实现工厂接口
- 关键：创建过程在子类执行
- 场景：1. 运行时才能确定对象的类型；2. 数据库切换，动态配置不同数据库类型的连接
- 优点：1. 将具体产品和创建者解耦；2. 符合单一职责原则；3. 扩展性高，如果想增加一个产品，只要扩展一个工厂类就可以
- 缺点：1. 每次增加一个产品时，都需要增加一个具体类和对象实现工厂，使得系统中类的个数成倍增加

## 源码应用

Calendar.getInstance()
java.text.NumberFormat.getInstance()
java.util.ResourceBundle.getBundle()

java.net.URLStreamHandlerFactory
javax.xml.bind.JAXBContext.createMarshaller

## Demo实现

### 实现方式

1. 创建一个英雄接口，实现该接口。接口类即为具体的英雄；
2. 定义一个工厂类，功能：传入英雄名称，即可以返回指定英雄的实例对象；

### 图例

![img](https://notes-yc-01.oss-cn-shenzhen.aliyuncs.com/blog/2020/11/16/a499881b181346eaaf69bff48d695c29.png)

### 步骤

1. 创建一个接口 

   `Legend.java`

2. 创建接口实现类 

   `Garen.java, XinZhao.java, Teemo.java`

3. 创建一个工厂类，根据指定输入参数，生成对应的实例对象 `LegendFactory`

4. 使用上一步的工厂类，传如类型信息，获取对象，调用方法

   `FactoryPatternDemo.java`

5. 执行流程