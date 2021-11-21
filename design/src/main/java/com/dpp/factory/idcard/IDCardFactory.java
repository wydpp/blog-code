package com.dpp.factory.idcard;

import com.dpp.factory.framework.Factory;
import com.dpp.factory.framework.Product;

import java.util.ArrayList;
import java.util.List;

public class IDCardFactory extends Factory {

    private List owners = new ArrayList();

    @Override
    protected Product createProduct(String owner) {
        return new IDCard(owner);
    }

    @Override
    protected void registerProduct(Product product) {
        owners.add(product);
    }

    public List getOwners(){
        return owners;
    }
}
