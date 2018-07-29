package com.dpp.factory.framework;

/**
 * 使用了Template Method模式。声明了用于"生成产品"的createProduct抽象方法和用于"注册产品"的registerProduct抽象方法。
 * "生成产品"和"注册产品"的具体处理则交给了Factory类的子类负责。
 * 工厂是用来调用create方法生成Product实例的。create方法先是调用createProduct生成产品，接着调用registerProduct注册产品。
 */
public abstract class Factory {
    public final Product create(String owner){
        Product p = createProduct(owner);
        registerProduct(p);
        return p;
    }
    protected abstract Product createProduct(String owner);
    protected abstract void registerProduct(Product product);
}
