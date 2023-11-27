package com.coursework.eshop.fxController;

import com.coursework.eshop.StartGui;
import com.coursework.eshop.hibernateController.GenericHibernate;
import com.coursework.eshop.model.*;
import jakarta.persistence.EntityManagerFactory;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class MainShopController implements Initializable {

    @FXML
    public ListView<Product> productList;
    @FXML
    public ListView<Cart> currentOrder;
    @FXML
    public Tab productTab;
    @FXML
    public Tab usersTab;
    @FXML
    public Tab warehouseTab;
    @FXML
    public ListView<Warehouse> warehouseList;
    @FXML
    public Tab ordersTab;
    @FXML
    public TableView<Customer> customerTable;
    @FXML
    public TableView<Manager> managerTable;
    @FXML
    public TabPane tabPane;
    @FXML
    public Tab primaryTab;
    @FXML
    public ListView<Product> productListManager;
    @FXML
    public TextField productTitleField;
    @FXML
    public TextArea productDescriptionField;
    @FXML
    public ComboBox<ProductType> productType;
    @FXML
    public ComboBox<Warehouse> warehouseComboBox;
    @FXML
    public TextField socketField;
    @FXML
    public TextField chipsetField;
    @FXML
    public TextField coreFrequencyField;
    @FXML
    public TextField tdpField;
    @FXML
    public TextField memoryTypeField;
    @FXML
    public TextField memorySizeField;
    @FXML
    public TextField memoryFrequencyField;
    @FXML
    public TextField coreCountField;
    @FXML
    public Tab otherTab;
    @FXML
    public TextField warehouseTitleField;
    @FXML
    public TextField warehouseAddressField;
    @FXML
    public TextField productManufacturerField;

    private EntityManagerFactory entityManagerFactory;
    private User currentUser;
    private GenericHibernate genericHibernate;

    public void setData(EntityManagerFactory entityManagerFactory, User user) {
        this.entityManagerFactory = entityManagerFactory;
        this.currentUser = user;
        limitAccess();
        loadData();
    }

    private void loadData() {
        genericHibernate = new GenericHibernate(entityManagerFactory);
        productList.getItems().clear();
        List<Product> allProducts = genericHibernate.readAllRecords(Product.class);
        productList.getItems().addAll(allProducts);
    }

    private void loadWarehouseList() {
        warehouseList.getItems().clear();
        warehouseList.getItems().addAll(genericHibernate.readAllRecords(Warehouse.class));
    }

    private void limitAccess() {
        if (currentUser.getClass() == Manager.class) {
            Manager manager = (Manager) currentUser;
            if (!manager.isAdministrator()) {
                managerTable.setDisable(true);
            }
        } else if (currentUser.getClass() == Customer.class) {
            Customer customer = (Customer) currentUser;
            tabPane.getTabs().remove(warehouseTab);
            tabPane.getTabs().remove(usersTab);
        } else {
            JavaFxCustomUtils.generateAlert(
                    Alert.AlertType.ERROR,
                    "Access denied",
                    "You have no access to this page",
                    "Please, contact your administrator");
        }
    }

    public void leaveComment() {
    }

    public void addToCart() {
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        productType.getItems().addAll(ProductType.values());
    }

    public void loadTabValues() {
        if (productTab.isSelected()) {
            loadProductListManager();
            warehouseComboBox.getItems().clear();
            warehouseComboBox.getItems().addAll(genericHibernate.readAllRecords(Warehouse.class));
        } else if (warehouseTab.isSelected()) {
            loadWarehouseList();
        }

    }

    public void enableProductFields() {
        if(productType.getSelectionModel().getSelectedItem() == ProductType.CPU) {
            socketField.setDisable(false);
            coreCountField.setDisable(false);
            coreFrequencyField.setDisable(false);
            tdpField.setDisable(false);
            chipsetField.setDisable(true);
            memoryTypeField.setDisable(true);
            memorySizeField.setDisable(true);
            memoryFrequencyField.setDisable(true);
        } else if (productType.getSelectionModel().getSelectedItem() == ProductType.MOTHERBOARD) {
            socketField.setDisable(false);
            chipsetField.setDisable(false);
            memoryTypeField.setDisable(false);
            memorySizeField.setDisable(false);
            memoryFrequencyField.setDisable(false);
            coreCountField.setDisable(true);
            coreFrequencyField.setDisable(true);
            tdpField.setDisable(true);
        } else if (productType.getSelectionModel().getSelectedItem() == ProductType.GRAPHICS_CARD) {
            memoryTypeField.setDisable(false);
            memorySizeField.setDisable(false);
            memoryFrequencyField.setDisable(false);
            coreFrequencyField.setDisable(false);
            tdpField.setDisable(false);
            socketField.setDisable(true);
            chipsetField.setDisable(true);
            coreCountField.setDisable(true);
        }
    }

    public void logout() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(StartGui.class.getResource("login.fxml"));
        Parent parent = fxmlLoader.load();
        LoginController loginController = fxmlLoader.getController();
        loginController.setData(entityManagerFactory);
        Scene scene = new Scene(parent);
        Stage stage = (Stage) productTitleField.getScene().getWindow();
        stage.setTitle("Login");
        stage.setScene(scene);
        stage.show();
    }

    public void addNewWarehouse() {
        genericHibernate.create(new Warehouse(warehouseTitleField.getText(), warehouseAddressField.getText()));
        loadWarehouseList();
    }

    public void updateExistingWarehouse() {
        Warehouse selectedWarehouse = warehouseList.getSelectionModel().getSelectedItem();
        Warehouse warehouse = genericHibernate.readEntityById(Warehouse.class, selectedWarehouse.getId());
        warehouse.setTitle(warehouseTitleField.getText());
        warehouse.setAddress(warehouseAddressField.getText());
        genericHibernate.update(warehouse);
        loadWarehouseList();
    }

    public void removeExistingWarehouse() {
        Warehouse selectedWarehouse = warehouseList.getSelectionModel().getSelectedItem();
        genericHibernate.delete(Warehouse.class, selectedWarehouse.getId());
        loadWarehouseList();
    }

    public void loadWarehouseData() {
        Warehouse selectedWarehouse = warehouseList.getSelectionModel().getSelectedItem();
        warehouseTitleField.setText(selectedWarehouse.getTitle());
        warehouseAddressField.setText(selectedWarehouse.getAddress());
    }

    private void loadProductListManager() {
        productListManager.getItems().clear();
        productListManager.getItems().addAll(genericHibernate.readAllRecords(Product.class));
    }

    public void addNewProduct() {
        if (productType.getSelectionModel().getSelectedItem() == ProductType.CPU) {
            Warehouse selectedWarehouse = warehouseComboBox.getSelectionModel().getSelectedItem();
            genericHibernate.create(new CentralProcessingUnit(productTitleField.getText(), productDescriptionField.getText(), productManufacturerField.getText(), genericHibernate.readEntityById(Warehouse.class, selectedWarehouse.getId()), socketField.getText(), Integer.parseInt(coreCountField.getText()), Integer.parseInt(coreFrequencyField.getText()), Integer.parseInt(tdpField.getText())));
        } else if (productType.getSelectionModel().getSelectedItem() == ProductType.MOTHERBOARD) {
            Warehouse selectedWarehouse = warehouseComboBox.getSelectionModel().getSelectedItem();
            genericHibernate.create(new Motherboard(productTitleField.getText(), productDescriptionField.getText(), productManufacturerField.getText(), genericHibernate.readEntityById(Warehouse.class, selectedWarehouse.getId()), socketField.getText(), chipsetField.getText(), memoryTypeField.getText(), Integer.parseInt(memorySizeField.getText()), Integer.parseInt(memoryFrequencyField.getText())));
        } else if (productType.getSelectionModel().getSelectedItem() == ProductType.GRAPHICS_CARD) {
            Warehouse selectedWarehouse = warehouseComboBox.getSelectionModel().getSelectedItem();
            genericHibernate.create(new GraphicsCard(productTitleField.getText(), productDescriptionField.getText(), productManufacturerField.getText(), genericHibernate.readEntityById(Warehouse.class, selectedWarehouse.getId()), memoryTypeField.getText(), Integer.parseInt(memorySizeField.getText()), Integer.parseInt(memoryFrequencyField.getText()), Integer.parseInt(coreFrequencyField.getText()), Integer.parseInt(tdpField.getText())));
        }
        loadProductListManager();
    }

    public void updateProduct() {
        loadProductListManager();
    }

    public void deleteProduct() {
        loadProductListManager();
    }
}
