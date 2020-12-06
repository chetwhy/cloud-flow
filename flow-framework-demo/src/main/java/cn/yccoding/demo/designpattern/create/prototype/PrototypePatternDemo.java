package cn.yccoding.demo.designpattern.create.prototype;

/**
 * 原型模式--示例类
 *
 * @author YC
 * @since 2020/12/3
 */
public class PrototypePatternDemo {

    public static void main(String[] args) {
        LegendCache.loadCache();

        Legend teemo1 = LegendCache.getLegend("teemo");
        Legend teemo2 = LegendCache.getLegend("teemo");
        System.out.println(teemo1.hashCode());
        System.out.println(teemo2.hashCode());

        Teemo teemo = new Teemo();
        Teemo teemo3 = (Teemo) teemo.clone();
        Teemo teemo4 = (Teemo) teemo.clone();
        System.out.println(teemo3.hashCode());
        System.out.println(teemo4.hashCode());
    }
}
