package cn.yccoding.demo.designpattern.create.prototype;

/**
 * 盖伦
 *
 * @author YC
 * @since 2020/12/3
 */
public class Garen extends Legend{
    public Garen() {
        type = "garen123";
    }

    @Override
    String ult() {
        return "德玛西亚审判";
    }
}
