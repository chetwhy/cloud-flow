package cn.yccoding.demo.designpattern.create.builder;

/**
 * 装备建造者
 *
 * @author YC
 * @since 2020/11/29
 */
public class SuitBuilder {

    public Suit equipA() {
        Suit suit = new Suit();
        suit.addItem(new LostChapter());
        suit.addItem(new RelicShield());
        return suit;
    }

    public Suit equipB() {
        Suit suit = new Suit();
        suit.addItem(new LudensEcho());
        suit.addItem(new SpiritVisage());
        return suit;
    }
}
