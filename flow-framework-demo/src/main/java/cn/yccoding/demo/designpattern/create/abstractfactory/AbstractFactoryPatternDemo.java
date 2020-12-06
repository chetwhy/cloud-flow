package cn.yccoding.demo.designpattern.create.abstractfactory;

/**
 * 抽象工厂模式--演示类
 *
 * @author YC
 * @since 2020/11/19
 */
public class AbstractFactoryPatternDemo {

    public static void main(String[] args) {
        AbstractFactory itemFactory = FactoryProducer.getFactory("item");
        AbstractFactory legendFactory = FactoryProducer.getFactory("LEGEND");

        Item triforce = itemFactory.getItem("triforce");
        System.out.println(triforce.stat());

        Legend timo = legendFactory.getLegend("teemo");
        System.out.println(timo.ult());
    }
}
