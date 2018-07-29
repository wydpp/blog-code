package com.dpp.factory.idcard;

import com.dpp.factory.framework.Product;

public class IDCard extends Product {
    private String owner;
    protected IDCard(String owner){
        System.out.println("制作"+owner+"的卡.");
        this.owner = owner;
    }

    @Override
    public void use() {
        System.out.println("使用"+owner+"的卡。");
    }

    public String getOwner(){
        return owner;
    }
}
