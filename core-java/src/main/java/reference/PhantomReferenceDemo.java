package reference;

import java.lang.ref.PhantomReference;
import java.lang.ref.Reference;
import java.lang.ref.ReferenceQueue;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @ClassName PhantomreferenceDemo.java
 * @Author duanpengpeng
 * @Version 1.0.0
 * @Description 虚引用demo
 * @CreateTime 2022/12/09 14:44:00
 */
public class PhantomReferenceDemo {

    private static final List<Object> LIST = new LinkedList<>();

    private static final ReferenceQueue<ReferenceDemo> QUEUE = new ReferenceQueue<>();

    public static void main(String[] args) throws InterruptedException {
        //虚引用对象被回收时，会把一个信息添加到QUEUE中。
        PhantomReference<ReferenceDemo> phantomReference = new PhantomReference<>(new ReferenceDemo(), QUEUE);
        AtomicInteger cnt = new AtomicInteger();
        new Thread(() -> {
            while (true) {
                try {
                    //一次申请2M内存，内不能不足时，会回收phantomReference中的对象ReferenceDemo，然后QUEUE中会有添加相关信息
                    LIST.add(new byte[1024 * 1024 * 2]);
                    System.out.println(cnt.incrementAndGet());
                    //TimeUnit.SECONDS.sleep(1);
                } catch (Exception e) {
                    e.printStackTrace();
                    break;
                }
                System.out.println("虚引用="+phantomReference.get());
            }
        }).start();

        new Thread(() -> {
            while (true) {
                Reference<? extends ReferenceDemo> poll = QUEUE.poll();
                if (poll != null) {
                    System.out.println("---虚引用对象被jvm回收了----" + poll);
                    break;
                }
            }
        }).start();
        //应用 nio的DirectByteBuffer,管理堆外内存
    }
}
