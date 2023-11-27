package com.coursework.eshop.fxController;

import com.coursework.eshop.model.Product;
import com.coursework.eshop.model.User;
import jakarta.persistence.EntityManagerFactory;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;

public class MainShopController {

    @FXML
    public ListView<Product> productList;

    public void setData(EntityManagerFactory entityManagerFactory, User user) {
    }
}
