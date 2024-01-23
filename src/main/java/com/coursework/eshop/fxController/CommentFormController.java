package com.coursework.eshop.fxController;

import com.coursework.eshop.hibernateController.GenericHibernate;
import com.coursework.eshop.model.Comment;
import com.coursework.eshop.model.Product;
import com.coursework.eshop.model.Review;
import com.coursework.eshop.model.User;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class CommentFormController {
    @FXML
    public TextField commentTitleField;
    @FXML
    public TextArea commentBodyField;
    @FXML
    public Slider ratingField;
    @FXML
    public Label ratingLabel;

    private int productId = 0;
    private int commentId = 0;
    private GenericHibernate genericHib;
    private User currentUser;


    public void setData(GenericHibernate genericHib, int productId, int commentId, User currentUser) {
        this.genericHib = genericHib;
        this.productId = productId;
        this.commentId = commentId;
        this.currentUser = currentUser;
    }

    public void saveData() {

        if (productId != 0) {
            Product product = genericHib.readEntityById(Product.class, productId);
            Review review = new Review(commentTitleField.getText(), commentBodyField.getText(), currentUser, ratingField.getValue(), product);
            product.getReviews().add(review);
            genericHib.update(product);
        } else if (commentId != 0) {
            Comment parentComment = genericHib.readEntityById(Comment.class, commentId);
            Comment reply = new Comment(commentTitleField.getText(), commentBodyField.getText(), parentComment, parentComment.getUser());
            parentComment.getReplies().add(reply);
            genericHib.update(parentComment);
        }

    }

    @FXML
    public void initialize() {
        ratingField.valueProperty().addListener((obs, oldVal, newVal) -> {
            int roundedValue = (int) Math.round(newVal.doubleValue());
            ratingLabel.setText("Rating: " + roundedValue);
            ratingField.setValue(roundedValue);
        });
    }
}


