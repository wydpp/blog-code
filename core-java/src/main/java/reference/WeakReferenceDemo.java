package reference;

import java.lang.ref.WeakReference;
import java.sql.SQLOutput;

/**
 * @ClassName WeakRererenceDemo.java
 * @Author duanpengpeng
 * @Version 1.0.0
 * @Description 弱引用
 * @CreateTime 2022/12/09 14:03:00
 */
public class WeakReferenceDemo {
    /**
     * 弱引用，遇到gc就会被回收
     * @param args
     */
    public static void main(String[] args) {
        WeakReference<ReferenceDemo> m = new WeakReference<>(new ReferenceDemo());
        System.out.println(m.get());
        System.gc();
        //结果是null
        System.out.println(m.get());

        //弱引用使用场景
        /*
        弱引用遇到gc就会被回收
        ThreadLocalMap中的entry的key弱引用的是ThreadLocal，当ThreadLocal被回收时，因为是弱引用，所以会正常回收。
        内存溢出问题：ThreadLocalMap的key即ThreadLocal被回收时，key=null，此时value任然在map中，无法回收，因此必须调用ThreadLocal.remove()方法。
         */
        ThreadLocal<ReferenceDemo> tl = new ThreadLocal<>();
        tl.set(new ReferenceDemo());
        //使用完必须调用remove方法释放value
        tl.remove();
    }
}
