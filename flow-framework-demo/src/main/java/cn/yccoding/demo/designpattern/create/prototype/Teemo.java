package cn.yccoding.demo.designpattern.create.prototype;


/**
 * 提莫
 *
 * @author YC
 * @since 2020/11/15
 */
public class Teemo extends Legend{
    public Teemo() {
        type = "teemo123";
    }

    @Override
    public String ult() {
        return "Noxious Trap（种蘑菇）";
    }
}
