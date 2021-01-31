package cn.yccoding.demo.designpattern.struct.adapter.objadapter;

import cn.yccoding.demo.designpattern.struct.adapter.classadapter.Adaptee;
import cn.yccoding.demo.designpattern.struct.adapter.classadapter.Target;

/**
 * 对象适配器
 *
 * @author YC
 * @since 2021/1/28
 */
public class Adapter implements Target {
    private Adaptee adaptee;

    public Adapter(Adaptee adaptee) {
        this.adaptee = adaptee;
    }

    @Override
    public void request() {
        // do sth
        adaptee.adapteeRequest();
        // do sth
    }
}
