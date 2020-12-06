package cn.yccoding.demo.designpattern.create.abstractfactory;

/**
 * 工厂生成器,传入工厂名，返回工厂类
 *
 * @author YC
 * @since 2020/11/19
 */
public class FactoryProducer {

    public static AbstractFactory getFactory(String factory) {
        if ("legend".equalsIgnoreCase(factory)) {
            return new LegendFactory();
        } else if ("item".equalsIgnoreCase(factory)) {
            return new ItemFactory();
        }
        return null;
    }
}
