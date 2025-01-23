import util.LogUtil;

/**
 *
 * @author dpp
 * @date 2025/1/23
 * @Description 
 */
public class Task{
    /**
     * 任务所属对象
     */
    private Person person;
    /**
     * 任务
     */
    private Runnable task;
    /**
     * 时间间隔
     */
    private int delay;

    public Task(Person person, Runnable task, int delay) {
        this.person = person;
        this.task = task;
        this.delay = delay;
    }

    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }

    public Runnable getTask() {
        return task;
    }

    public void setTask(Runnable task) {
        this.task = task;
    }

    public void execute(){
        LogUtil.log(person.getName()+"正在执行任务");
        task.run();
        LogUtil.log(person.getName()+"任务执行完毕");
    }

    public int getDelay() {
        return delay;
    }

    public void setDelay(int delay) {
        this.delay = delay;
    }
}
