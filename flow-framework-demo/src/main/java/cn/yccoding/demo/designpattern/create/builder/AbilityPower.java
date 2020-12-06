package cn.yccoding.demo.designpattern.create.builder;

/**
 * 魔法伤害
 *
 * @author YC
 * @since 2020/11/29
 */
public abstract class AbilityPower implements Item {
    @Override
    public abstract double price();

    @Override
    public Equipment equip() {
        return new Attack();
    }
}
