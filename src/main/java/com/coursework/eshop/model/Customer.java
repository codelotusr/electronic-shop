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
    private String address;
    private String cardNo;

    public Customer(String username, String password, LocalDate birthDate, String firstName, String lastName, String address, String cardNo) {
        super(username, password, birthDate, firstName, lastName);
        this.address = address;
        this.cardNo = cardNo;
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
                '}';
    }
}
