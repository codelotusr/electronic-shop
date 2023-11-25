package com.coursework.eshop.fxController;

import com.coursework.eshop.model.GraphicsCard;
import com.coursework.eshop.model.Product;
import com.coursework.eshop.utils.DatabaseUtils;
import javafx.fxml.Initializable;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.ListView;

import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
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
                String memoryType = resultSet.getString(4);
                int memorySize = resultSet.getInt(5);
                int memoryFrequency = resultSet.getInt(6);
                int coreFrequency = resultSet.getInt(7);
                int tdp = resultSet.getInt(8);
                int powerSupply = resultSet.getInt(9);

                GraphicsCard graphicsCard = new GraphicsCard(id, title, description, memoryType, memorySize, memoryFrequency, coreFrequency, tdp, powerSupply);
                productsFromDb.add(graphicsCard);
            }

            productList.getItems().addAll(productsFromDb);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
