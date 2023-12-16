package com.coursework.eshop.model;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Review extends Comment {
    private double rating;
    @ManyToOne
    private Product product;

    public Review(String commentTitle, String commentBody, double rating, Product product) {
        super(commentTitle, commentBody);
        this.rating = rating;
        this.product = product;
    }

    @Override
    public String toString() {
        return "(" + rating + ") " + getCommentBody();
    }


}
