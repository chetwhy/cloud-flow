package cn.yccoding.demo.designpattern.create.abstractfactory;

/**
 * 三项之力
 *
 * @author YC
 * @since 2020/11/19
 */
public class TriForce implements Item {
    @Override
    public String stat() {
        return "AS,Crit,AD(攻速，暴击，攻击力)";
    }
}
