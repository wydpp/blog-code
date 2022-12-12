package reference;

/**
 * @ClassName ReferenceDemo.java
 * @Author duanpengpeng
 * @Version 1.0.0
 * @Description
 * @CreateTime 2022/12/09 13:29:00
 */
public class ReferenceDemo {

    @Override
    protected void finalize() throws Throwable {
        System.out.println("finalize被调用");
    }
}
