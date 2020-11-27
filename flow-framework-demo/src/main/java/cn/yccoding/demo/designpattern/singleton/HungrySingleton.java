package cn.yccoding.demo.designpattern.singleton;

/**
 * 单例模式--饿汉式
 *
 * @author YC
 * @since 2020/11/27
 */
public class HungrySingleton {

    /**
     * 类加载时就给静态变量赋值初始化
     */
    private static HungrySingleton instance = new HungrySingleton();

    /**
     * 构造器私有
     */
    private HungrySingleton(){}

    public static HungrySingleton getInstance() {
        return instance;
    }
}
