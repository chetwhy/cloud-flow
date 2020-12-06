package cn.yccoding.demo.designpattern.create.singleton;

/**
 * 单例模式--枚举
 *
 * @author YC
 * @since 2020/11/27
 */
public enum EnumSingleton {
    /**
     * 天然不支持反射创建对应的实例，且有自己的反序列化机制
     * 利用类加载机制保证线程安全
     */
    ENUM_INSTANCE("singleton","enum"),
    LAZY_INSTANCE("singleton","lazy");

    private String name;
    private String type;

    EnumSingleton(String name, String type) {
        this.name = name;
        this.type = type;
    }
}

