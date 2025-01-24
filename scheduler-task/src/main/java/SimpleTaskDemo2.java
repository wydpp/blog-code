import util.LogUtil;

import java.util.concurrent.TimeUnit;

/**
 * @author dpp
 * @date 2025/1/23
 * @Description
 */
public class SimpleTaskDemo2 {
    /**
     * 添加、执行定时任务
     *
     * @param task 任务
     */
    public static void main(String[] args) {
        Runnable eat = new Runnable() {
            @Override
            public void run() {
                LogUtil.log("吃饭中~~~~~");
                sleepSeconds(3);
            }
        };
        TaskScheduler taskScheduler = new TaskScheduler();
        //张三3秒后开始吃饭
        Person person = new Person("张三");
        int delay = 3;
        Task zhangSanTask = new Task(person, eat, delay);
        taskScheduler.submit(zhangSanTask);
        //张三3秒后开始吃饭
        Person lisi = new Person("李四");
        Task lisiTask = new Task(lisi, eat, 2);
        taskScheduler.submit(lisiTask);
    }

    private static void sleepSeconds(int seconds) {
        try {
            TimeUnit.SECONDS.sleep(seconds);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

}
