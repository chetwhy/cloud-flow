package cn.yccoding.demo.designpattern.create.builder;

/**
 * 防御
 *
 * @author YC
 * @since 2020/11/29
 */
public class Defense implements Equipment {
    @Override
    public String equip() {
        return "defense";
    }
}
