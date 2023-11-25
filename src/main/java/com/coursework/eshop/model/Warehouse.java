package com.coursework.eshop.model;

import java.util.List;

public class Warehouse {
    private int id;
    private String address;
    private List<Product> productsInStock;
    // TODO add more fields


    public Warehouse(String address, List<Product> productsInStock) {
        this.address = address;
        this.productsInStock = productsInStock;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public List<Product> getProductsInStock() {
        return productsInStock;
    }

    public void setProductsInStock(List<Product> productsInStock) {
        this.productsInStock = productsInStock;
    }
}
