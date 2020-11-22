package cn.yccoding.demo.designpattern.abstractfactory;

/**
 * 抽象工厂
 *
 * @author YC
 * @since 2020/11/19
 */
public abstract class AbstractFactory {
    public abstract Legend getLegend(String legend);
    public abstract Item getItem(String item);
}
