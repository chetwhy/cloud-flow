package cn.yccoding.demo.designpattern.create.builder;

/**
 * 遗失的章节
 *
 * @author YC
 * @since 2020/11/29
 */
public class LostChapter extends AbilityPower{
    @Override
    public String name() {
        return "鬼书";
    }

    @Override
    public double price() {
        return 2700.0;
    }
}
