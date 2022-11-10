package concurrent;

import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.TimeUnit;

/**
 * @ClassName CachedThreadPool.java
 * @Author duanpengpeng
 * @Version 1.0.0
 * @Description
 * @CreateTime 2022/11/09 17:46:00
 */
public class SynchronousQueueDemo {

    private static void test1() throws InterruptedException {
        //如果使用SynchronousQueue，且worker线程只有1个，则woker未执行完毕之前加任务会阻塞
        SynchronousQueue<String> synchronousQueue = new SynchronousQueue<>();
        ////任务1没有被消费之前，put操作会阻塞，
        System.out.println("任务1开始添加");
        synchronousQueue.put("任务1");//阻塞
        System.out.println("任务1添加完成");//这一行不会执行
    }

    private static void test2() throws InterruptedException {
        //如果使用SynchronousQueue，且worker线程只有1个，则woker未执行完毕之前加任务会阻塞
        SynchronousQueue<String> synchronousQueue = new SynchronousQueue<>();
        ////任务1没有被消费之前，put操作会阻塞，
        Thread t1 = new Thread(() -> {
            try {
                System.out.println(Thread.currentThread().getState().name());//RUNNABLE
                System.out.println("任务1开始添加");
                synchronousQueue.put("任务1");
                System.out.println("任务1添加完成");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        t1.start();
        TimeUnit.SECONDS.sleep(1);
        System.out.println(t1.getState().name());//WAITING
        System.out.println("开始消费任务1");
        String msg = synchronousQueue.take();
        System.out.println(t1.getState().name());//WAITING or TERMINATED
        System.out.println("获取到的任务=" + msg);
        System.out.println(t1.getState().name());//TERMINATED
    }

    public static void main(String[] args) throws InterruptedException {
//        test1();
        test2();
    }
}
