package cn.yccoding.demo.designpattern.struct.adapter.classadapter;

/**
 * 类适配器
 *
 * @author YC
 * @since 2021/1/28
 */
public class Adapter extends Adaptee implements Target{
    @Override
    public void request() {
        // do sth
        super.adapteeRequest();
        // do sth
    }
}
