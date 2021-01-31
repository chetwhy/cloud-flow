package cn.yccoding.demo.designpattern.struct.adapter.classadapter;

/**
 * 目标实现类
 *
 * @author YC
 * @since 2021/1/28
 */
public class ConcreteTarget implements Target{

    @Override
    public void request() {
        System.out.println("ConcreteTarget.request method");
    }
}
