package com.coursework.eshop.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Dairy extends Product {
    private LocalDate expiryDate;

    public Dairy(int id, String title, String description, LocalDate expiryDate) {
        super(id, title, description);
        this.expiryDate = expiryDate;
    }

    @Override
    public String toString() {
        return "Dairy{" +
                "id=" + id +
                ", name=" + name +
                ", description=" + description +
                ", expiryDate=" + expiryDate +
                '}';
    }
}
