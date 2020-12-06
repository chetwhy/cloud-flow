package cn.yccoding.demo.designpattern.create.simplefactory;

/**
 * 工厂模式--演示类
 *
 * @author YC
 * @since 2020/11/15
 */
public class SimpleFactoryPatternDemo {

    public static void main(String[] args) {
        LegendFactory factory = new LegendFactory();
        String garenUlt = factory.getLegend("garen").ult();
        String teemoUlt = factory.getLegend("teemo").ult();
        System.out.println(garenUlt);
        System.out.println(teemoUlt);
    }
}
