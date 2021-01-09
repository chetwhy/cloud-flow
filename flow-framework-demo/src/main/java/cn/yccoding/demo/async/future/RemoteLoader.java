package cn.yccoding.demo.async.future;

/**
 * 模拟远程接口
 *
 * @author YC
 * @since 2021/1/9
 */
public interface RemoteLoader {

    /**
     * 远程接口方法
     * @return
     */
    String load();
}
