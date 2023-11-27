package com.coursework.eshop.fxController;

import com.coursework.eshop.model.Cart;
import com.coursework.eshop.model.Product;
import com.coursework.eshop.model.User;
import com.coursework.eshop.model.Warehouse;
import jakarta.persistence.EntityManagerFactory;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.control.Tab;
import javafx.scene.control.TextField;

public class MainShopController {

    @FXML
    public ListView<Product> productList;
    @FXML
    public ListView<Cart> currentOrder;
    @FXML
    public Tab productsTab;
    @FXML
    public Tab usersTab;
    @FXML
    public Tab warehouseTab;
    @FXML
    public ListView<Warehouse> warehouseList;
    @FXML
    public TextField addressField;
    @FXML
    public TextField titleField;
    @FXML
    public Tab ordersTab;

    public void setData(EntityManagerFactory entityManagerFactory, User user) {
    }

    public void leaveComment(ActionEvent actionEvent) {
    }

    public void addToCart(ActionEvent actionEvent) {
    }
}
