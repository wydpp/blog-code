package reference;

import java.util.concurrent.TimeUnit;

/**
 * @ClassName NormalRererence.java
 * @Author duanpengpeng
 * @Version 1.0.0
 * @Description 强引用
 * @CreateTime 2022/12/09 13:31:00
 */
public class NormalReferenceDemo {

    public static void main(String[] args) throws InterruptedException {
        //demo是强引用
        ReferenceDemo demo = new ReferenceDemo();
        demo = null;
        System.gc();
        TimeUnit.SECONDS.sleep(1);
    }
}
