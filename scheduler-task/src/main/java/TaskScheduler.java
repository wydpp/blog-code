import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author dpp
 * @date 2025/1/23
 * @Description
 */
public class TaskScheduler {
    //执行任务的线程池
    private ExecutorService workers = Executors.newFixedThreadPool(10);
    //任务队列
    private List<TaskWrapper> tasks = new LinkedList<>();

    private class TaskWrapper implements Runnable {
        //任务
        Task task;
        //任务执行的时间
        long executeTime;

        public TaskWrapper(Task task) {
            this.task = task;
            this.executeTime = System.currentTimeMillis() + task.getDelay() * 1000;
        }

        @Override
        public void run() {

        }
    }

    public TaskScheduler() {
        new Thread(() -> {
            schedule();
        }).start();
    }

    //提交任务
    public void submit(Task task) {
        synchronized (tasks) {
            tasks.add(new TaskWrapper(task));
            // 排序任务队列，把最先要执行的放在队列前面
            Collections.sort(tasks, (o1, o2) -> (int) (o1.executeTime - o2.executeTime));
            tasks.notify();
        }
    }

    public void schedule() {
        while (true) {
            synchronized (tasks) {
                while (tasks.isEmpty()) {//没有任务，等待
                    try {
                        //等待submit唤醒，释放锁
                        tasks.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                //取第一个任务，判断是否已经过期，过期则执行，否则等待
                TaskWrapper taskWrapper = tasks.get(0);
                if (taskWrapper.executeTime <= System.currentTimeMillis()) {
                    workers.execute(() -> taskWrapper.task.execute());
                    //已经执行的任务，放入过期队列，等待删除
                    tasks.remove(taskWrapper);
                }else {
                    try {
                        //等待执行时间,释放锁
                        tasks.wait(taskWrapper.executeTime - System.currentTimeMillis());
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

}
