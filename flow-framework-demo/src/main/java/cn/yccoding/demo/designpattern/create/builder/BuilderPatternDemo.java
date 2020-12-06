package cn.yccoding.demo.designpattern.create.builder;

/**
 * 建造者模式--示例类
 *
 * @author YC
 * @since 2020/11/29
 */
public class BuilderPatternDemo {

    public static void main(String[] args) {
        SuitBuilder suitBuilder = new SuitBuilder();

        Suit suit1 = suitBuilder.equipA();
        suit1.showItems();
        System.out.println(suit1.getMoney());

        System.out.println("==========");

        Suit suit2 = suitBuilder.equipB();
        suit2.showItems();
        System.out.println(suit2.getMoney());
    }
}
