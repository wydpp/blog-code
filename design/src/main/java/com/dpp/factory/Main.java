package com.dpp.factory;

import com.dpp.factory.framework.Factory;
import com.dpp.factory.framework.Product;
import com.dpp.factory.idcard.IDCardFactory;

public class Main {

    public static void main(String[] args) {
        Factory factory = new IDCardFactory();
        Product card1 = factory.create("小明");
        Product card2 = factory.create("丽丽");
        Product card3 = factory.create("妞妞");
        card1.use();
        card2.use();
        card3.use();
    }
}
