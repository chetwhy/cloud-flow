package cn.yccoding.demo.designpattern.factory;

/**
 * 英雄工厂，输入英雄名，返回对应英雄
 *
 * @author YC
 * @since 2020/11/15
 */
public class LegendFactory {

    /**
     * 获取英雄的类
     * @param name
     * @return
     */
    public Legend getLegend(String name){
        if (name == null) {
            return null;
        }
        if (name.equalsIgnoreCase("garen")) {
            return new Garen();
        } else if (name.equalsIgnoreCase("xinzhao")) {
            return new XinZhao();
        } else if (name.equalsIgnoreCase("teemo")) {
            return new Teemo();
        }
        return null;
    }
}
