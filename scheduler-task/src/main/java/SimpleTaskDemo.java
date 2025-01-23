import util.LogUtil;

import java.util.concurrent.TimeUnit;

/**
 * @author dpp
 * @date 2025/1/23
 * @Description
 */
public class SimpleTaskDemo {
    /**
     * 添加、执行定时任务
     *
     * @param task 任务
     */
    public static void submit(Task task) {
        LogUtil.log("添加任务");
        // 延迟delay秒执行任务
        sleepSeconds(task.getDelay());
        // 执行任务
        task.execute();
    }

    public static void main(String[] args) {

        Runnable eat = new Runnable() {
            @Override
            public void run() {
                LogUtil.log("吃饭中~~~~~");
                sleepSeconds(3);
            }
        };

        //张三3秒后开始吃饭
        Person person = new Person("张三");
        int delay = 3;
        Task zhangSanTask = new Task(person, eat, delay);
        submit(zhangSanTask);
        Person lisi = new Person("李四");
        Task lisiTask = new Task(lisi, eat, delay);
        submit(lisiTask);
    }

    private static void sleepSeconds(int seconds) {
        try {
            TimeUnit.SECONDS.sleep(seconds);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

}
