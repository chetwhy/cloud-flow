package cn.yccoding.demo.designpattern.abstractfactory;

/**
 * 工厂生成器,传入工厂名，返回工厂类
 *
 * @author YC
 * @since 2020/11/19
 */
public class FactoryProducer {

    public static AbstractFactory getFactory(String factory) {
        if (factory.equalsIgnoreCase("legend")) {
            return new LegendFactory();
        } else if (factory.equalsIgnoreCase("item")) {
            return new ItemFactory();
        }
        return null;
    }
}
