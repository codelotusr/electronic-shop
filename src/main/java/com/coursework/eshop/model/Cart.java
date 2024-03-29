package com.coursework.eshop.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Cart {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private LocalDate dateCreated;
    private double cart_value;
    private String cartStatus;

    @OneToMany(mappedBy = "cart", cascade = CascadeType.ALL, orphanRemoval = true)
    @LazyCollection(LazyCollectionOption.FALSE)
    private List<Product> itemsInCart;
    @ManyToOne
    @JoinColumn(name = "customer_id", nullable = false)
    private User owner;
    @ManyToOne
    @JoinColumn(name = "manager_id")
    private Manager manager;

    public Cart(int id, double cart_value, User owner, String cartStatus, Manager manager) {
        this.id = id;
        this.owner = owner;
        this.dateCreated = LocalDate.now();
        this.cart_value = cart_value;
        this.cartStatus = "Pending";
        this.manager = manager;
    }

    public double getCartValue() {
        return cart_value;
    }

    public int getOwnerId() {
        return owner.getId();
    }
}