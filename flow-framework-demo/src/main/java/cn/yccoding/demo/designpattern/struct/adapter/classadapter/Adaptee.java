package cn.yccoding.demo.designpattern.struct.adapter.classadapter;

/**
 * 被适配的对象
 *
 * @author YC
 * @since 2021/1/28
 */
public class Adaptee {

    public void adapteeRequest() {
        System.out.println("被适配者的方法");
    }
}
