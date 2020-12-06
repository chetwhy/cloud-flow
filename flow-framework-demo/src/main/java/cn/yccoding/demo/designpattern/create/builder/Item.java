package cn.yccoding.demo.designpattern.create.builder;

/**
 * 召唤师
 *
 * @author YC
 * @since 2020/11/29
 */
public interface Item {
    /**
     * 名称
     */
    String name();

    /**
     * 属性
     */
    double price();

    /**
     * 装备
     *
     * @return
     */
    Equipment equip();
}
