package cn.yccoding.demo.designpattern.struct.adapter.classadapter;

/**
 * 测试类
 *
 * @author YC
 * @since 2021/1/28
 */
public class ClassAdapterDemo {

    public static void main(String[] args) {
        Target target = new ConcreteTarget();
        target.request();

        // 新接口使用旧类方法
        Target adapter = new Adapter();
        adapter.request();
    }
}
