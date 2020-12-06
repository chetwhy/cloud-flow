package cn.yccoding.demo.designpattern.create.builder;

/**
 * 振奋盔甲（绿甲）
 *
 * @author YC
 * @since 2020/11/29
 */
public class SpiritVisage extends MagicResistance{

    @Override
    public String name() {
        return "绿甲";
    }

    @Override
    public double price() {
        return 2600.0;
    }
}
