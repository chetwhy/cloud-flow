package cn.yccoding.demo.async.future;

import java.util.List;
import java.util.function.Supplier;

/**
 * 测试类
 *
 * @author YC
 * @since 2021/1/9
 */
public class CompareMethodMain {

    private static final LocalClient localClient = new LocalClient();

    public static void main(String[] args) {
        execute("sync", () -> localClient.findSequential());
        execute("parallel", () -> localClient.findParallel());
        execute("future", () -> localClient.findFuture());
        execute("comFuture", () -> localClient.findCompletableFuture());
    }

    private static void execute(String msg, Supplier<List<String>> s) {
        long start = System.nanoTime();
        System.out.println("remote interface response = " + s.get());
        long duration = (System.nanoTime() - start) / 1_000_000;
        System.out.println(msg + " done in " + duration + " msecs");
    }
}
