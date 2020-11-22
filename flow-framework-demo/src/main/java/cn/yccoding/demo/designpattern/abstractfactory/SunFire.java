package cn.yccoding.demo.designpattern.abstractfactory;

/**
 * 装备属性
 *
 * @author YC
 * @since 2020/11/19
 */
public class SunFire implements Item {
    @Override
    public String stat() {
        return "ar(护甲)";
    }
}
