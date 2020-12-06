package cn.yccoding.demo.designpattern.create.prototype;

import java.util.concurrent.ConcurrentHashMap;

/**
 * 英雄缓存
 *
 * @author YC
 * @since 2020/12/3
 */
public class LegendCache {

    private static ConcurrentHashMap<String, Legend> legendCache = new ConcurrentHashMap<>();

    public static Legend getLegend(String name) {
        Legend legend = legendCache.get(name);
        return (Legend) legend.clone();
    }

    public static void loadCache() {
        Legend garen = new Garen();
        garen.setName("garen");
        legendCache.put("garen",garen);

        Legend teemo = new Teemo();
        garen.setName("teemo");
        legendCache.put("teemo",teemo);

        Legend xinZhao = new XinZhao();
        garen.setName("xinzhao");
        legendCache.put("xinzhao",xinZhao);
    }
}
