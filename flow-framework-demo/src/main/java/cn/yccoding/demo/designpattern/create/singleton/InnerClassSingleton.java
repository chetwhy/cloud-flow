package cn.yccoding.demo.designpattern.create.singleton;

import java.io.ObjectStreamException;
import java.io.Serializable;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

/**
 * 单例模式--静态内部类
 *
 * @author YC
 * @since 2020/11/27
 */
public class InnerClassSingleton implements Serializable {
    static final long serialVersionUID = 1L;

    private InnerClassSingleton() {
        // 防止反射破坏
        if (InnerClassHolder.instance != null) {
            throw new RuntimeException("单例模式不允许多个实例");
        }
    }

    public static InnerClassSingleton getInstance() {
        return InnerClassHolder.instance;
    }

    /**
     * 模拟反射攻击
     *
     * @param args
     */
    public static void main(String[] args) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
        Constructor<InnerClassSingleton> declaredConstructor = InnerClassSingleton.class.getDeclaredConstructor();
        declaredConstructor.setAccessible(true);
        InnerClassSingleton innerClassSingleton = declaredConstructor.newInstance();
        InnerClassSingleton instance = InnerClassSingleton.getInstance();
        System.out.println(innerClassSingleton == instance);
    }

    /**
     * 提供readResolve()方法，防反序列化
     * 当JVM反序列化地恢复一个新对象时，系统会自动调用这个readResolve()方法返回指定好的对象，从而保证系统通过反序列化机制不会产生多个java对象
     *
     * @return
     * @throws ObjectStreamException
     */
    Object readResolve() throws ObjectStreamException {
        return InnerClassHolder.instance;
    }

    /**
     * 实例的创建，只有世纪调用外部类时，出发静态内部类的初始化，属于懒加载
     */
    private static class InnerClassHolder {
        private static final InnerClassSingleton instance = new InnerClassSingleton();
    }
}
