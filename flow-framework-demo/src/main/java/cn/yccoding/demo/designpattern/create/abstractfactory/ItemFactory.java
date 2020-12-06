package cn.yccoding.demo.designpattern.create.abstractfactory;

/**
 * 装备工厂类，扩展自抽象工厂
 *
 * @author YC
 * @since 2020/11/19
 */
public class ItemFactory extends AbstractFactory {
    @Override
    public Legend getLegend(String legend) {
        return null;
    }

    @Override
    public Item getItem(String item) {
        if (item.equalsIgnoreCase("sunfire")) {
            return new SunFire();
        } else if (item.equalsIgnoreCase("triforce")) {
            return new TriForce();
        } else if (item.equalsIgnoreCase("zhonya")) {
            return new ZhonYa();
        }
        return null;
    }
}
