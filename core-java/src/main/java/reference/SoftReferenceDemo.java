package reference;

import java.lang.ref.SoftReference;
import java.util.concurrent.TimeUnit;

/**
 * @ClassName SoftReference.java
 * @Author duanpengpeng
 * @Version 1.0.0
 * @Description 软引用示例
 * @CreateTime 2022/12/09 13:35:00
 */
public class SoftReferenceDemo {
    /**
     * 运行方法时要设置-Xmx20M
     * @param args
     * @throws InterruptedException
     */
    public static void main(String[] args) throws InterruptedException {
        //m是强引用，但是SoftReference对象内部对于new byte[1024*1024*10]数组是软引用
        //软件用在内存不够用的情况下可能会被回收
        //申请了10M内存
        SoftReference<byte[]> m = new SoftReference<>(new byte[1024*1024*10]);
        System.out.println(m.get());
        System.gc();
        TimeUnit.SECONDS.sleep(1);
        //有值
        System.out.println(m.get());
        //再次申请10M内存
        byte[] bytes = new byte[1024*1024*10];
        //结果是null
        System.out.println("打印结果："+m.get());
    }
}
