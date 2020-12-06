package cn.yccoding.demo.designpattern.create.builder;

/**
 * 圣物之盾
 *
 * @author YC
 * @since 2020/11/29
 */
public class RelicShield extends MagicResistance{
    @Override
    public String name() {
        return "圣物之盾";
    }

    @Override
    public double price() {
        return 2500.0;
    }
}
