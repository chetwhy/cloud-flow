package cn.yccoding.demo.designpattern.create.builder;

/**
 * 魔抗
 *
 * @author YC
 * @since 2020/11/29
 */
public abstract class MagicResistance implements Item {
    @Override
    public abstract double price();

    @Override
    public Equipment equip() {
        return new Defense();
    }
}
