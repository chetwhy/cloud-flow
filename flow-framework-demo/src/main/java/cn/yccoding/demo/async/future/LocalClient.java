package cn.yccoding.demo.async.future;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.*;
import java.util.stream.Collectors;

/**
 * 模拟本地客户端，批量调用远程接口
 *
 * @author YC
 * @since 2021/1/9
 */
public class LocalClient {

    final int cores = Runtime.getRuntime().availableProcessors() * 2;
    /**
     * 远程接口集合
     */
    private final List<RemoteLoader> remoteLoaders = Arrays.asList(new AccountService(), new EmployeeService());
    /**
     * 创建线程池
     */
    private final ExecutorService executorService =
        Executors.newFixedThreadPool(remoteLoaders.size(), new ThreadFactory() {
            @Override
            public Thread newThread(Runnable r) {
                Thread t = new Thread(r);
                t.setDaemon(true);
                return t;
            }
        });
    private final ExecutorService executorService2 =
        new ThreadPoolExecutor(cores, cores, 0L, TimeUnit.MILLISECONDS, new ArrayBlockingQueue<>(1024), r -> {
            Thread t = new Thread(r);
            t.setName("test-executor");
            t.setDaemon(true);
            return t;
        }, new ThreadPoolExecutor.AbortPolicy());

    /**
     * 同步方法
     */
    public List<String> findSequential() {
        return remoteLoaders.stream().map(RemoteLoader::load).collect(Collectors.toList());
    }

    /**
     * java8并行流
     */
    public List<String> findParallel() {
        return remoteLoaders.parallelStream().map(RemoteLoader::load).collect(Collectors.toList());
    }

    /**
     * java7 future
     */
    public List<String> findFuture() {
        // 整理future集合
        List<Future<String>> futureList =
            remoteLoaders.stream().map(loader -> executorService.submit(loader::load)).collect(Collectors.toList());
        // 从future获取各线程结果
        List<String> collect = futureList.stream().map(future -> {
            try {
                return future.get(2, TimeUnit.SECONDS);
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                // 线程异常，关闭
                future.cancel(true);
            }
            return null;
        }).filter(Objects::nonNull).collect(Collectors.toList());
        return collect;
    }

    /**
     * java8 CompletableFuture
     */
    public List<String> findCompletableFuture() {
        // 整理future集合
        List<CompletableFuture<String>> futureList =
            remoteLoaders.stream().map(loader -> CompletableFuture.supplyAsync(loader::load, executorService2))
                .map(future -> future.thenApply(i -> i + "test")).collect(Collectors.toList());
        // 从future获取各线程结果
        List<String> collect = futureList.stream().map(CompletableFuture::join).collect(Collectors.toList());
        return collect;
    }
}
