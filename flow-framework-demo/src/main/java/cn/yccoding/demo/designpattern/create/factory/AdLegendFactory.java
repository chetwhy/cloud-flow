package cn.yccoding.demo.designpattern.create.factory;


/**
 * 英雄工厂，输入英雄名，返回对应ad类型英雄
 *
 * @author YC
 * @since 2020/11/15
 */
public class AdLegendFactory {

    /**
     * 获取英雄的类
     * @param name
     * @return
     */
    public Legend getLegend(String name){

        if ("garen".equalsIgnoreCase(name)) {
            Garen garen = new Garen();
            garen.setGarenMethod("德玛西亚");
            return garen;
        } else if ("xinzhao".equalsIgnoreCase(name)) {
            return new Teemo();
        }
        return null;
    }
}
