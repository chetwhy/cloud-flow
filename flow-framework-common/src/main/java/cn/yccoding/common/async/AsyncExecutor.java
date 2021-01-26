package cn.yccoding.common.async;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

import javax.annotation.PostConstruct;

import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

/**
 * 异步执行器
 *
 * @author YC
 * @since 2021/1/10
 */
@Slf4j
@Component
public class AsyncExecutor implements InvocationHandler {
    private static final ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext();
    private ThreadPoolTaskExecutor executor;
    private ThreadLocal<Object> localTarget;
    private ThreadLocal<String> localTaskId;
    private String className;
    private String methodName;
    private final ThreadLocal<ConcurrentHashMap<String, Future<?>>> cache = new ThreadLocal<>();
    private final ThreadLocal<Map<Class<?>, Object>> proxyInstanceCache = new ThreadLocal<>();

    @PostConstruct
    private void init() {
        if (executor == null) {
            ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext();
            Object defaultExecutor = context.getBean("asyncDefaultExecutor");
            if (defaultExecutor != null) {
                executor = (ThreadPoolTaskExecutor)defaultExecutor;
            } else {
                executor = new ThreadPoolTaskExecutor();
                executor.setCorePoolSize(20);
            }
        }
    }

    public void init(ThreadPoolTaskExecutor customExecutor) {
        if (customExecutor != null) {
            executor = customExecutor;
        }
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        long start = System.currentTimeMillis();
        String methodName = method.getName();
        method.setAccessible(true);
        saveCacheData(localTaskId.get(), localTarget.get(), method, args);
        log.info("async spends = [{}]", System.currentTimeMillis() - start);
        return null;
    }

    public <T> T instance(String asyncTaskId, String vi, Class<T> cls) {
        if (asyncTaskId == null || vi == null || cls == null) {
            return null;
        }
        if (localTarget == null) {
            localTarget = new ThreadLocal<>();
        }
        if (localTaskId == null) {
            localTaskId = new ThreadLocal<>();
        }
        localTarget.set(context.getBean(cls));
        localTaskId.set(asyncTaskId.concat(vi));
        this.className = cls.getSimpleName();
        return this.getProxyInstance(cls);
    }

    public Object instance(String taskId, Class<?> cls) {
        return instance(taskId, "", cls);
    }

    public Object getResult(String taskId, String vi) {
        long start = System.currentTimeMillis();
        if (taskId == null || vi == null) {
            return null;
        }
        String taskName = taskId.concat(vi);
        ConcurrentHashMap<String, Future<?>> map = cache.get();
        if (map == null) {
            return null;
        }
        Future<?> future = map.get(taskName);
        if (future != null) {
            try {
                future.get(3000L, TimeUnit.MILLISECONDS);
            } catch (Exception e) {
                log.error("sth was wrong, msg = [{}]", e.getMessage());
            } finally {
                future.cancel(true);
            }
        }
        return null;
    }

    public <T> T getResult(String taskId, String vi, Class<T> cls) {
        Object result = getResult(taskId, vi);
        return result == null ? null : (T)result;
    }

    private <T> T getProxyInstance(Class<T> cls) {
        long start = System.currentTimeMillis();
        Map<Class<?>, Object> map = proxyInstanceCache.get();
        if (map == null) {
            map = new HashMap<>();
        }
        Object proxyInstance = map.get(cls);
        if (proxyInstance == null) {
            proxyInstance =
                Proxy.newProxyInstance(cls.getClassLoader(), cls.getInterfaces(), context.getBean(AsyncExecutor.class));
            map.put(cls, proxyInstance);
            proxyInstanceCache.set(map);
        }
        log.info("spends = [{}]", System.currentTimeMillis() - start);
        return (T)proxyInstance;
    }

    private synchronized void saveCacheData(String taskId, Object target, Method method, Object[] args) {
        AsyncTask asyncTask = new AsyncTask(target, taskId, args, method);
        Future<Object> future = executor.submit(asyncTask);
        ConcurrentHashMap<String, Future<?>> map = cache.get();
        if (map == null) {
            map = new ConcurrentHashMap<>();
        }
        map.put(taskId, future);
        cache.set(map);
    }

}
