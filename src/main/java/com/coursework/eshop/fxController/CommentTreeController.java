package com.coursework.eshop.fxController;

import com.coursework.eshop.StartGui;
import com.coursework.eshop.hibernateController.CustomHibernate;
import com.coursework.eshop.hibernateController.EntityManagerFactorySingleton;
import com.coursework.eshop.model.Comment;
import com.coursework.eshop.model.Manager;
import com.coursework.eshop.model.Product;
import com.coursework.eshop.model.User;
import jakarta.persistence.EntityManagerFactory;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ListView;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;


public class CommentTreeController implements Initializable {
    @FXML
    public TreeView<Comment> commentsTree;
    @FXML
    public ListView<Product> productListReview;

    private CustomHibernate customHibernate;
    private User currentUser;

    public void setData(CustomHibernate customHibernate, User currentUser) {
        this.customHibernate = customHibernate;
        this.currentUser = currentUser;
        loadProducts();
    }

    private void loadProducts() {
        if (productListReview.getItems() != null) {
            productListReview.getItems().clear();
        }
        productListReview.getItems().addAll(customHibernate.readAllRecords(Product.class));

    }

    public void deleteComment() {
        TreeItem<Comment> commentTreeItem = commentsTree.getSelectionModel().getSelectedItem();
        if (commentTreeItem != null) {
            Comment comment = commentTreeItem.getValue();

            if (canModifyComment(comment, currentUser)) {
                customHibernate.deleteComment(comment.getId());
            } else {
                JavaFxCustomUtils.generateAlert(Alert.AlertType.ERROR, "Error", "You cannot delete this comment", "You are not the owner of this comment");
            }
        }
        loadComments();
    }


    private boolean canModifyComment(Comment comment, User currentUser) {
        if (comment.getUser().equals(currentUser)) {
            return true;
        }

        if (currentUser instanceof Manager) {
            Manager manager = (Manager) currentUser;
            return manager.isAdministrator();
        }

        return false;
    }

    public void reply() throws IOException {
        TreeItem<Comment> commentTreeItem = commentsTree.getSelectionModel().getSelectedItem();
        FXMLLoader fxmlLoader = new FXMLLoader(StartGui.class.getResource("commentForm.fxml"));
        Parent parent = fxmlLoader.load();
        CommentFormController commentForm = fxmlLoader.getController();
        commentForm.setData(customHibernate, 0, commentTreeItem.getValue().getId(), currentUser);
        Stage stage = new Stage();
        Scene scene = new Scene(parent);
        stage.setTitle("E-Shop");
        stage.setScene(scene);
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.showAndWait();
        loadComments();
    }

    public void loadComments() {
        Product selectedProduct = customHibernate.readEntityById(Product.class, productListReview.getSelectionModel().getSelectedItem().getId());
        commentsTree.setRoot(new TreeItem<>());
        commentsTree.setShowRoot(false);
        commentsTree.getRoot().setExpanded(true);
        selectedProduct.getReviews().forEach(comment -> addTreeItem(comment, commentsTree.getRoot()));
    }

    private void addTreeItem(Comment comment, TreeItem<Comment> parentComment) {
        TreeItem<Comment> treeItem = new TreeItem<>(comment);
        parentComment.getChildren().add(treeItem);
        comment.getReplies().forEach(sub -> addTreeItem(sub, treeItem));
    }

    public void loadResponseForm() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(StartGui.class.getResource("commentForm.fxml"));
        Parent parent = fxmlLoader.load();
        CommentFormController commentForm = fxmlLoader.getController();
        commentForm.setData(customHibernate, productListReview.getSelectionModel().getSelectedItem().getId(), 0, currentUser);
        Stage stage = new Stage();
        Scene scene = new Scene(parent);
        stage.setTitle("Shop");
        stage.setScene(scene);
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.showAndWait();
        loadComments();
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    public void returnToMainShop() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(StartGui.class.getResource("main-shop.fxml"));
        Parent parent = fxmlLoader.load();
        MainShopController mainShopController = fxmlLoader.getController();
        mainShopController.setData(EntityManagerFactorySingleton.getEntityManagerFactory(), currentUser);
        Scene scene = new Scene(parent);
        Stage stage = (Stage) productListReview.getScene().getWindow();
        stage.setTitle("Shop");
        stage.setScene(scene);
        stage.show();
    }
}