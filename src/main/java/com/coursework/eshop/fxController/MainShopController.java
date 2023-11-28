package com.coursework.eshop.fxController;

import com.coursework.eshop.StartGui;
import com.coursework.eshop.fxController.tableViews.CustomerTableParameters;
import com.coursework.eshop.hibernateController.CustomHibernate;
import com.coursework.eshop.model.*;
import jakarta.persistence.EntityManagerFactory;
import javafx.beans.Observable;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.stage.Stage;
import javafx.util.Callback;

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
    @FXML
    public TextField commentTitleField;
    @FXML
    public TextArea commentBodyField;
    @FXML
    public ListView<Comment> commentList;
    @FXML
    public Tab commentTab;
    @FXML
    public TableView customerTable;
    @FXML
    public TableView managerTable;
    @FXML
    public TableColumn<CustomerTableParameters, Integer> customerIdColumn;
    @FXML
    public TableColumn<CustomerTableParameters, String> customerUsernameColumn;
    @FXML
    public TableColumn<CustomerTableParameters, String> customerPasswordColumn;
    @FXML
    public TableColumn<CustomerTableParameters, String> customerAddressColumn;
    @FXML
    public TableColumn<CustomerTableParameters, String> customerCardNoColumn;
    @FXML
    public TableColumn<CustomerTableParameters, String> customerNameColumn;
    @FXML
    public TableColumn<CustomerTableParameters, String> customerSurnameColumn;
    @FXML
    public TableColumn<CustomerTableParameters, Void> dummyColumn;


    private ObservableList<CustomerTableParameters> customerTableParametersObservableList = FXCollections.observableArrayList();
    private EntityManagerFactory entityManagerFactory;
    private User currentUser;
    private CustomHibernate customHibernate;

    public void setData(EntityManagerFactory entityManagerFactory, User user) {
        this.entityManagerFactory = entityManagerFactory;
        this.currentUser = user;
        limitAccess();
        loadData();
    }

    private void loadData() {
        customHibernate = new CustomHibernate(entityManagerFactory);
        productList.getItems().clear();
        List<Product> allProducts = customHibernate.readAllRecords(Product.class);
        productList.getItems().addAll(allProducts);
    }

    private void loadWarehouseList() {
        warehouseList.getItems().clear();
        warehouseList.getItems().addAll(customHibernate.readAllRecords(Warehouse.class));
    }

    private void loadCommentList() {
        commentList.getItems().clear();
        commentList.getItems().addAll(customHibernate.readAllRecords(Comment.class));
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
        customerTableParams();
    }

    private void customerTableParams() {
        customerTable.setEditable(true);
        customerIdColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        customerUsernameColumn.setCellValueFactory(new PropertyValueFactory<>("username"));
        customerPasswordColumn.setCellValueFactory(new PropertyValueFactory<>("password"));
        customerPasswordColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        customerPasswordColumn.setOnEditCommit(event -> {
            event.getTableView().getItems().get(event.getTablePosition().getRow()).setPassword(event.getNewValue());
            Customer customer = customHibernate.readEntityById(Customer.class, event.getTableView().getItems().get(event.getTablePosition().getRow()).getId());
            customer.setPassword(event.getNewValue());
            customHibernate.update(customer);
        });
        customerAddressColumn.setCellValueFactory(new PropertyValueFactory<>("address"));
        customerCardNoColumn.setCellValueFactory(new PropertyValueFactory<>("cardNo"));
        customerNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        customerSurnameColumn.setCellValueFactory(new PropertyValueFactory<>("surname"));

        Callback<TableColumn<CustomerTableParameters, Void>, TableCell<CustomerTableParameters, Void>> callback = param -> {
            final TableCell<CustomerTableParameters, Void> cell = new TextFieldTableCell<>() {
                private final Button deleteButton = new Button("Delete");
                {
                    deleteButton.setOnAction(event -> {
                        CustomerTableParameters row = getTableView().getItems().get(getIndex());
                        customHibernate.delete(Customer.class, row.getId());
                        customerTable.getItems().remove(row);
                    });
                }
                @Override
                public void updateItem(Void item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty) {
                        setGraphic(null);
                    } else {
                        setGraphic(deleteButton);
                    }
                }
            };
            return cell;
        };
        dummyColumn.setCellFactory(callback);
    }

    public void loadTabValues() {
        if (productTab.isSelected()) {
            loadProductListManager();
            warehouseComboBox.getItems().clear();
            warehouseComboBox.getItems().addAll(customHibernate.readAllRecords(Warehouse.class));
        } else if (warehouseTab.isSelected()) {
            loadWarehouseList();
        } else if (commentTab.isSelected()) {
            loadCommentList();
        } else if (usersTab.isSelected()) {
            loadUserTables();
        }

    }

    private void loadUserTables() {
        customerTable.getItems().clear();
        List<Customer> customerList = customHibernate.readAllRecords(Customer.class);
        for (Customer customer : customerList) {
            CustomerTableParameters customerTableParameters = new CustomerTableParameters();
            customerTableParameters.setId(customer.getId());
            customerTableParameters.setUsername(customer.getUsername());
            customerTableParameters.setPassword(customer.getPassword());
            customerTableParameters.setName(customer.getFirstName());
            customerTableParameters.setSurname(customer.getLastName());
            customerTableParameters.setAddress(customer.getAddress());
            customerTableParameters.setCardNo(customer.getCardNo());
            customerTableParametersObservableList.add(customerTableParameters);
        }
        customerTable.setItems(customerTableParametersObservableList);

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
        customHibernate.create(new Warehouse(warehouseTitleField.getText(), warehouseAddressField.getText()));
        loadWarehouseList();
    }

    public void updateExistingWarehouse() {
        Warehouse selectedWarehouse = warehouseList.getSelectionModel().getSelectedItem();
        Warehouse warehouse = customHibernate.readEntityById(Warehouse.class, selectedWarehouse.getId());
        warehouse.setTitle(warehouseTitleField.getText());
        warehouse.setAddress(warehouseAddressField.getText());
        customHibernate.update(warehouse);
        loadWarehouseList();
    }

    public void removeExistingWarehouse() {
        Warehouse selectedWarehouse = warehouseList.getSelectionModel().getSelectedItem();
        customHibernate.delete(Warehouse.class, selectedWarehouse.getId());
        loadWarehouseList();
    }

    public void loadWarehouseData() {
        Warehouse selectedWarehouse = warehouseList.getSelectionModel().getSelectedItem();
        warehouseTitleField.setText(selectedWarehouse.getTitle());
        warehouseAddressField.setText(selectedWarehouse.getAddress());
    }

    private void loadProductListManager() {
        productListManager.getItems().clear();
        productListManager.getItems().addAll(customHibernate.readAllRecords(Product.class));
    }

    public void addNewProduct() {
        if (productType.getSelectionModel().getSelectedItem() == ProductType.CPU) {
            Warehouse selectedWarehouse = warehouseComboBox.getSelectionModel().getSelectedItem();
            customHibernate.create(new CentralProcessingUnit(productTitleField.getText(), productDescriptionField.getText(), productManufacturerField.getText(), customHibernate.readEntityById(Warehouse.class, selectedWarehouse.getId()), socketField.getText(), Integer.parseInt(coreCountField.getText()), Integer.parseInt(coreFrequencyField.getText()), Integer.parseInt(tdpField.getText())));
        } else if (productType.getSelectionModel().getSelectedItem() == ProductType.MOTHERBOARD) {
            Warehouse selectedWarehouse = warehouseComboBox.getSelectionModel().getSelectedItem();
            customHibernate.create(new Motherboard(productTitleField.getText(), productDescriptionField.getText(), productManufacturerField.getText(), customHibernate.readEntityById(Warehouse.class, selectedWarehouse.getId()), socketField.getText(), chipsetField.getText(), memoryTypeField.getText(), Integer.parseInt(memorySizeField.getText()), Integer.parseInt(memoryFrequencyField.getText())));
        } else if (productType.getSelectionModel().getSelectedItem() == ProductType.GRAPHICS_CARD) {
            Warehouse selectedWarehouse = warehouseComboBox.getSelectionModel().getSelectedItem();
            customHibernate.create(new GraphicsCard(productTitleField.getText(), productDescriptionField.getText(), productManufacturerField.getText(), customHibernate.readEntityById(Warehouse.class, selectedWarehouse.getId()), memoryTypeField.getText(), Integer.parseInt(memorySizeField.getText()), Integer.parseInt(memoryFrequencyField.getText()), Integer.parseInt(coreFrequencyField.getText()), Integer.parseInt(tdpField.getText())));
        }
        loadProductListManager();
    }

    public void updateProduct() {
        if (productType.getSelectionModel().getSelectedItem() == ProductType.CPU) {
            Warehouse selectedWarehouse = warehouseComboBox.getSelectionModel().getSelectedItem();
            CentralProcessingUnit selectedProduct = (CentralProcessingUnit) productListManager.getSelectionModel().getSelectedItem();
            CentralProcessingUnit product = customHibernate.readEntityById(CentralProcessingUnit.class, selectedProduct.getId());
            product.setTitle(productTitleField.getText());
            product.setDescription(productDescriptionField.getText());
            product.setManufacturer(productManufacturerField.getText());
            product.setWarehouse(customHibernate.readEntityById(Warehouse.class, selectedWarehouse.getId()));
            product.setSocket(socketField.getText());
            product.setCoreCount(Integer.parseInt(coreCountField.getText()));
            product.setCoreFrequency(Integer.parseInt(coreFrequencyField.getText()));
            product.setTdp(Integer.parseInt(tdpField.getText()));
            customHibernate.update(product);
        } else if (productType.getSelectionModel().getSelectedItem() == ProductType.MOTHERBOARD) {
            Warehouse selectedWarehouse = warehouseComboBox.getSelectionModel().getSelectedItem();
            Motherboard selectedProduct = (Motherboard) productListManager.getSelectionModel().getSelectedItem();
            Motherboard product = customHibernate.readEntityById(Motherboard.class, selectedProduct.getId());
            product.setTitle(productTitleField.getText());
            product.setDescription(productDescriptionField.getText());
            product.setManufacturer(productManufacturerField.getText());
            product.setWarehouse(customHibernate.readEntityById(Warehouse.class, selectedWarehouse.getId()));
            product.setSocket(socketField.getText());
            product.setChipset(chipsetField.getText());
            product.setMemoryType(memoryTypeField.getText());
            product.setMaxMemorySize(Integer.parseInt(memorySizeField.getText()));
            product.setMaxMemoryFrequency(Integer.parseInt(memoryFrequencyField.getText()));
            customHibernate.update(product);
        } else if (productType.getSelectionModel().getSelectedItem() == ProductType.GRAPHICS_CARD) {
            Warehouse selectedWarehouse = warehouseComboBox.getSelectionModel().getSelectedItem();
            GraphicsCard selectedProduct = (GraphicsCard) productListManager.getSelectionModel().getSelectedItem();
            GraphicsCard product = customHibernate.readEntityById(GraphicsCard.class, selectedProduct.getId());
            product.setTitle(productTitleField.getText());
            product.setDescription(productDescriptionField.getText());
            product.setManufacturer(productManufacturerField.getText());
            product.setWarehouse(customHibernate.readEntityById(Warehouse.class, selectedWarehouse.getId()));
            product.setMemoryType(memoryTypeField.getText());
            product.setMemorySize(Integer.parseInt(memorySizeField.getText()));
            product.setMemoryFrequency(Integer.parseInt(memoryFrequencyField.getText()));
            product.setCoreFrequency(Integer.parseInt(coreFrequencyField.getText()));
            product.setTdp(Integer.parseInt(tdpField.getText()));
            customHibernate.update(product);
        }
        loadProductListManager();
    }

    public void deleteProduct() {
        Product selectedProduct = productListManager.getSelectionModel().getSelectedItem();
        customHibernate.deleteProduct(selectedProduct.getId());
        loadProductListManager();
    }

    public void addNewComment() {
        customHibernate.create(new Comment(commentTitleField.getText(), commentBodyField.getText(), currentUser));
        loadCommentList();
    }

    public void updateExistingComment() {
        Comment selectedComment = commentList.getSelectionModel().getSelectedItem();
        Comment comment = customHibernate.readEntityById(Comment.class, selectedComment.getId());
        comment.setCommentTitle(commentTitleField.getText());
        comment.setCommentBody(commentBodyField.getText());
        customHibernate.update(comment);
        loadCommentList();
    }

    public void deleteExistingComment() {
        Comment selectedComment = commentList.getSelectionModel().getSelectedItem();
        customHibernate.deleteComment(selectedComment.getId());
        loadCommentList();
    }

    public void selectComment() {
        Comment selectedComment = commentList.getSelectionModel().getSelectedItem();
        commentTitleField.setText(selectedComment.getCommentTitle());
        commentBodyField.setText(selectedComment.getCommentBody());

    }

    public void exitShop() {
        System.exit(0);
    }
}
