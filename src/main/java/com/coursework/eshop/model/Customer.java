package com.coursework.eshop.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Customer extends User {
    private Address address;
    private Card card;

    public Customer(String username, String password, LocalDate birthDate, String firstName, String lastName, Address address, Card card) {
        super(username, password, birthDate, firstName, lastName);
        this.address = address;
        this.card = card;
    }

    @Override
    public String toString() {
        return "Customer{" +
                "username=" + username +
                ", password=" + password +
                ", birthDate=" + birthDate +
                ", firstName=" + firstName +
                ", lastName=" + lastName +
                ", address=" + address +
                ", card=" + card +
                '}';
    }
}
