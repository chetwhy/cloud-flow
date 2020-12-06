package cn.yccoding.demo.designpattern.create.builder.builder2;

/**
 * 建造者模式--示例类
 *
 * @author YC
 * @since 2020/12/2
 */
public class BuilderPatternDemo {
    public static void main(String[] args) {
        Champ champ = Champ.builder().nickname("zhao xin").setPassive("fight").build();
        System.out.println(champ);
    }
}
