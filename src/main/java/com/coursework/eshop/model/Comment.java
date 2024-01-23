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

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
public class Comment {
    @Id
    @GeneratedValue(strategy = jakarta.persistence.GenerationType.IDENTITY)
    private int id;
    private String commentTitle;
    private String commentBody;
    private LocalDate dateCreated;
    @OneToMany(mappedBy = "parentComment", cascade = CascadeType.ALL, orphanRemoval = true)
    @LazyCollection(LazyCollectionOption.FALSE)
    private List<Comment> replies;
    @ManyToOne
    private Comment parentComment;
    @ManyToOne
    private User user;

    public Comment(String commentTitle, String commentBody, User user) {
        this.commentTitle = commentTitle;
        this.commentBody = commentBody;
        this.dateCreated = LocalDate.now();
        this.user = user;
        this.replies = new ArrayList<>();
    }

    public Comment(String commentTitle, String commentBody, Comment parentComment, User user) {
        this.commentTitle = commentTitle;
        this.commentBody = commentBody;
        this.dateCreated = LocalDate.now();
        this.user = user;
        this.parentComment = parentComment;
        this.replies = new ArrayList<>();
    }

    @Override
    public String toString() {
        return user.getFirstName() + " " + user.getLastName() + ":" + commentTitle + ":" + dateCreated;
    }
}
