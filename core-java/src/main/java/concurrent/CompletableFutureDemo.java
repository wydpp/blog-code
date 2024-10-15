package concurrent;

import java.util.concurrent.*;

/**
 * @author dpp
 * @date 2024/6/28
 * @Description CompletableFuture示例
 */
public class CompletableFutureDemo {

    public static class Task implements Callable<String> {

        private String name;

        private Integer sleepTime;

        public Task(String name, Integer sleepTime) {
            this.name = name;
            this.sleepTime = sleepTime;
        }

        @Override
        public String call() {
            System.out.println("task:" + name + " start");
            try {
                TimeUnit.SECONDS.sleep(sleepTime);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            System.out.println("task:" + name + " end");
            return name;
        }
    }

    static ExecutorService executorservice = Executors.newCachedThreadPool();

    private static void testExecutorService() throws ExecutionException, InterruptedException {

        Long startTime = System.currentTimeMillis();
        Task task1 = new Task("task1", 1);
        Task task2 = new Task("task2", 1);
        Task task4 = new Task("task4", 1);
        Future<String> future1 = executorservice.submit(task1);
        Future<String> future2 = executorservice.submit(task2);
        //task3要依赖task1和task2的返回结果
        String s1 = future1.get();
        String s2 = future2.get();
        //不依赖前面task的task4
        Future<String> future4 = executorservice.submit(task4);
        Task task3 = new Task("task3:" + s1 + s2, 1);
        Future<String> future3 = executorservice.submit(task3);
        String result = future3.get();
        //task3和task4结果合并得到最终结果
        result = result + "_" + future4.get();
        System.out.println("最终结果result=" + result);
        System.out.println("耗时：" + (System.currentTimeMillis() - startTime));
//        executorservice.shutdown();
    }

    private static void testCompletableFuture() throws ExecutionException, InterruptedException {
        Long startTime = System.currentTimeMillis();
        CompletableFuture<String> task1 = CompletableFuture.supplyAsync(() -> new Task("task1", 1).call());
        CompletableFuture<String> task2 = CompletableFuture.supplyAsync(() -> new Task("task2", 1).call());
        CompletableFuture<String> task4 = CompletableFuture.supplyAsync(() -> new Task("task4", 1).call());
        CompletableFuture<String> task3 = task1.thenCombine(task2, (s1, s2) -> {
            //task3要依赖task1和task2的返回结果
            Task task = new Task("task3:"+s1 + s2, 1);
            task.call();
            return task.name;
        });
        task3.thenCombine(task4, (s3, s4) -> {
            return s3 + "_" + s4;
        }).thenAccept(result -> {
            System.out.println("最终结果result=" + result);
        }).join();
        System.out.println("耗时：" + (System.currentTimeMillis() - startTime));
    }

    public static void main(String[] args) throws Exception {
        testExecutorService();
//        testCompletableFuture();
//        Thread.sleep(TimeUnit.SECONDS.toMillis(10));
    }
}
