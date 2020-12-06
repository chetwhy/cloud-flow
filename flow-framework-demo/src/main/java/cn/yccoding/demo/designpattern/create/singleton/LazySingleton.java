package cn.yccoding.demo.designpattern.create.singleton;

/**
 * 单例模式--懒汉式
 *
 * @author YC
 * @since 2020/11/27
 */
public class LazySingleton {

    /**
     * 静态属性，可见性，防止指令重排
     */
    private volatile static LazySingleton instance;

    /**
     * 构造器私有
     */
    private LazySingleton() {
    }

    public static LazySingleton getInstance() {
        // DCL
        if (instance == null) {
            synchronized (LazySingleton.class) {
                if (instance == null) {
                    return new LazySingleton();
                }
            }
        }
        return instance;
    }
}
