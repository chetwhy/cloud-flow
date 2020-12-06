package cn.yccoding.demo.designpattern.create.builder;

/**
 * 卢登的回升
 *
 * @author YC
 * @since 2020/11/29
 */
public class LudensEcho extends AbilityPower{
    @Override
    public String name() {
        return "卢登的回升";
    }

    @Override
    public double price() {
        return 3100.0;
    }
}
