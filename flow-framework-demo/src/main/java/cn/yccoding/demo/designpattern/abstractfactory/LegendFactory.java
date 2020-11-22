package cn.yccoding.demo.designpattern.abstractfactory;

/**
 * 英雄工厂类，扩展自抽象工厂
 * 注：抽象工厂类，更建议使用接口interface，使用默认方法default修饰
 * @author YC
 * @since 2020/11/19
 */
public class LegendFactory extends AbstractFactory{
    @Override
    public Legend getLegend(String legend) {
        if (legend == null) {
            return null;
        }
        if (legend.equalsIgnoreCase("garen")) {
            return new Garen();
        } else if (legend.equalsIgnoreCase("xinzhao")) {
            return new XinZhao();
        } else if (legend.equalsIgnoreCase("teemo")) {
            return new Teemo();
        }
        return null;
    }

    @Override
    public Item getItem(String item) {
        return null;
    }
}
