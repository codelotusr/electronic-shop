package com.coursework.eshop.model;

import java.time.LocalDate;

public class Card {
    int id;
    String cardNumber;
    String cardHolder;
    LocalDate expirationDate;
    String cvv;

    public Card(String cardNumber, String cardHolder, LocalDate expirationDate, String cvv) {
        this.cardNumber = cardNumber;
        this.cardHolder = cardHolder;
        this.expirationDate = expirationDate;
        this.cvv = cvv;
    }

    public Card(int id, String cardNumber, String cardHolder, LocalDate expirationDate, String cvv) {
        this.id = id;
        this.cardNumber = cardNumber;
        this.cardHolder = cardHolder;
        this.expirationDate = expirationDate;
        this.cvv = cvv;
    }

    @Override
    public String toString() {
        return "Card{" +
                "cardNumber=" + cardNumber +
                ", cardHolder=" + cardHolder +
                ", expirationDate=" + expirationDate +
                ", cvv=" + cvv +
                '}';
    }
}
