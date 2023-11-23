package com.coursework.eshop.fxController;

import com.coursework.eshop.model.Dairy;
import com.coursework.eshop.model.Product;
import com.coursework.eshop.utils.DatabaseUtils;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;

import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class MainShopController implements Initializable {
    public ListView<Product> productList;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Connection connection = DatabaseUtils.connectDb();
        try {
            ArrayList<Product> productsFromDb = new ArrayList<>();
            Statement statement = connection.createStatement();
            String query = "SELECT * FROM product";
            ResultSet resultSet = statement.executeQuery(query);
            while (resultSet.next()) {
                int id = resultSet.getInt(1);
                String title = resultSet.getString(2);
                String description = resultSet.getString(3);
                LocalDate expiryDate = resultSet.getDate(4).toLocalDate();

                Dairy dairy = new Dairy(id, title, description, expiryDate);
                productsFromDb.add(dairy);
            }

            productList.getItems().addAll(productsFromDb);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
