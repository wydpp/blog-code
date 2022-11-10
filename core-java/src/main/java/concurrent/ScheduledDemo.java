package concurrent;

import java.util.Date;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * @ClassName ScheduledDemo.java
 * @Author duanpengpeng
 * @Version 1.0.0
 * @Description
 * @CreateTime 2022/11/09 15:41:00
 */
public class ScheduledDemo {
    public static void main(String[] args) {
        ScheduledExecutorService scheduledExecutorService = Executors.newScheduledThreadPool(10);
        System.out.println("当前时间" + new Date());
        scheduledExecutorService.schedule(() -> {
            System.out.println(new Date());
        }, 5, TimeUnit.SECONDS);
//        scheduledExecutorService.scheduleAtFixedRate(() -> {
//            System.out.println("开始调度任务" + new Date());
//            try {
//                TimeUnit.SECONDS.sleep(5);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//            System.out.println("调度任务结束" + new Date());
//        }, 5, 2, TimeUnit.SECONDS);
        scheduledExecutorService.scheduleWithFixedDelay(() -> {
            System.out.println("开始调度任务2" + new Date());
            try {
                TimeUnit.SECONDS.sleep(5);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("调度任务结束2" + new Date());
        }, 5, 2, TimeUnit.SECONDS);
    }
}
