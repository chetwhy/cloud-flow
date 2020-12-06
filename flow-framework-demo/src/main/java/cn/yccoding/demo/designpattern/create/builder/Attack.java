package cn.yccoding.demo.designpattern.create.builder;

/**
 * 攻击装备
 *
 * @author YC
 * @since 2020/11/29
 */
public class Attack implements Equipment {
    @Override
    public String equip() {
        return "attack";
    }
}
