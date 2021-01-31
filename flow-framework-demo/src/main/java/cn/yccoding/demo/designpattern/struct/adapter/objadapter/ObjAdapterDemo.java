package cn.yccoding.demo.designpattern.struct.adapter.objadapter;

import cn.yccoding.demo.designpattern.struct.adapter.classadapter.Adaptee;
import cn.yccoding.demo.designpattern.struct.adapter.classadapter.Target;

/**
 * 测试类
 *
 * @author YC
 * @since 2021/1/28
 */
public class ObjAdapterDemo {

    public static void main(String[] args) {
        Target target = new Adapter(new Adaptee());
        target.request();
    }
}
