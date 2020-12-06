package cn.yccoding.demo.designpattern.create.factory;

/**
 * 工厂模式--演示类
 *
 * @author YC
 * @since 2020/11/15
 */
public class FactoryPatternDemo {

    public static void main(String[] args) {
        AdLegendFactory adFactory = new AdLegendFactory();
        ApLegendFactory apFactory = new ApLegendFactory();
        String garenUlt = adFactory.getLegend("garen").ult();
        String teemoUlt = apFactory.getLegend("teemo").ult();
        System.out.println(garenUlt);
        System.out.println(teemoUlt);
    }
}
