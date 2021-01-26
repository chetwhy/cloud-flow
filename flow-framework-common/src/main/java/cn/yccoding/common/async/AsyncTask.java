package cn.yccoding.common.async;

import java.lang.reflect.Method;
import java.util.concurrent.Callable;

/**
 * 异步任务
 *
 * @author YC
 * @since 2021/1/10
 */
public class AsyncTask implements Callable<Object> {
    private Object target;
    private String taskId;
    private Object[] args;
    private Method method;

    public AsyncTask(Object target, String taskId, Object[] args, Method method) {
        this.target = target;
        this.taskId = taskId;
        this.args = args;
        this.method = method;
    }

    @Override
    public Object call() throws Exception {
        return method.invoke(target, args);
    }
}
