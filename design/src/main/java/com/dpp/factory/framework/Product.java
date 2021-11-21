package com.dpp.factory.framework;

/**
 * 定义了产品的类，仅声明了use抽象方法。use方法的实现交给了Product的子类负责。
 * 在这个框架中，定义了产品是"任何的可以use的"东西。
 */
public abstract class Product {
    public abstract void use();

}
