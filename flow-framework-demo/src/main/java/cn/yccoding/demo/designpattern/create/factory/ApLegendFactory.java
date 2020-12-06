package cn.yccoding.demo.designpattern.create.factory;

/**
 * 英雄工厂，输入英雄名，返回对应ap类型英雄
 *
 * @author YC
 * @since 2020/11/15
 */
public class ApLegendFactory {

    /**
     * 获取英雄的类
     *
     * @param name
     * @return
     */
    public Legend getLegend(String name) {
        if ("teemo".equalsIgnoreCase(name)) {
            return new Teemo();
        }
        return null;
    }
}
