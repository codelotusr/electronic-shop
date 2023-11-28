package com.coursework.eshop.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.ArrayList;


@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
public class Product implements Serializable {
    @Id
    @GeneratedValue(strategy = jakarta.persistence.GenerationType.IDENTITY)
    int id;
    String title;
    String description;
    String manufacturer;

    @ManyToOne
    @JoinColumn(name = "cart_id")
    Cart cart;

    @ManyToOne
    Warehouse warehouse;

    public Product(String name, String description) {
        this.title = title;
        this.description = description;
    }

    public Product(String title, String description, String manufacturer, Warehouse warehouse) {
        this.title = title;
        this.description = description;
        this.manufacturer = manufacturer;
        this.warehouse = warehouse;
    }

    public Product(String title, String description, String manufacturer) {
        this.title = title;
        this.description = description;
        this.manufacturer = manufacturer;
    }

    @Override
    public String toString() {
        return title + " " + manufacturer;
    }

}
