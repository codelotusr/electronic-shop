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
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Cart {
    @Id
    @GeneratedValue(strategy = jakarta.persistence.GenerationType.IDENTITY)
    private int id;
    private LocalDate dateCreated;
    @ManyToOne
    private Customer customer;
    @OneToMany(mappedBy = "cart", cascade = CascadeType.ALL, orphanRemoval = true)
    @LazyCollection(LazyCollectionOption.FALSE)
    private List<Product> itemsInCart = new ArrayList<>();


    public Cart(LocalDate dateCreated, Customer customer) {
        this.dateCreated = dateCreated;
        this.customer = customer;
    }

    public Cart(LocalDate dateCreated) {
        this.dateCreated = dateCreated;
    }

    public Cart(Customer customer) {
        this.customer = customer;
    }

    @Override
    public String toString() {
        return itemsInCart.toString();
    }




}
