package com.coursework.eshop.model;

import java.util.List;

public class Warehouse {
    private int id;
    private Address address;
    private List<Product> productsInStock;
    // TODO add more fields


    public Warehouse(Address address, List<Product> productsInStock) {
        this.address = address;
        this.productsInStock = productsInStock;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public List<Product> getProductsInStock() {
        return productsInStock;
    }

    public void setProductsInStock(List<Product> productsInStock) {
        this.productsInStock = productsInStock;
    }
}
