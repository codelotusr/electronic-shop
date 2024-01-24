package com.coursework.eshop.fxController;

import com.coursework.eshop.StartGui;
import com.coursework.eshop.fxController.tableViews.CartStatisticsTableParameters;
import com.coursework.eshop.fxController.tableViews.CartTableParameters;
import com.coursework.eshop.fxController.tableViews.CustomerTableParameters;
import com.coursework.eshop.fxController.tableViews.ManagerTableParameters;
import com.coursework.eshop.hibernateController.CustomHibernate;
import com.coursework.eshop.model.*;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.PieChart;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.util.Callback;
import javafx.util.converter.BooleanStringConverter;
import javafx.util.converter.DoubleStringConverter;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

public class MainShopController implements Initializable {

    @FXML
    public ListView<Product> productList;
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
    @FXML
    public TableColumn<ManagerTableParameters, Integer> managerIdColumn;
    @FXML
    public TableColumn<ManagerTableParameters, String> managerUsernameColumn;
    @FXML
    public TableColumn<ManagerTableParameters, String> managerPasswordColumn;
    @FXML
    public TableColumn<ManagerTableParameters, String> managerNameColumn;
    @FXML
    public TableColumn<ManagerTableParameters, String> managerSurnameColumn;
    @FXML
    public TableColumn<ManagerTableParameters, String> managerEmployeeIdColumn;
    @FXML
    public TableColumn<ManagerTableParameters, String> managerMedicalCertificationColumn;
    @FXML
    public TableColumn<ManagerTableParameters, Boolean> managerIsAdministratorColumn;
    @FXML
    public TableColumn<ManagerTableParameters, Void> managerDummyColumn;
    @FXML
    public AnchorPane mainAnchorPane;
    @FXML
    public Button adminRegister;
    @FXML
    public Label totalPriceLabel;
    @FXML
    public TextField productPriceField;
    @FXML
    public ListView<Product> currentItemsInCartList;
    @FXML
    public TableView cartTable;
    @FXML
    public TableColumn<CartTableParameters, Integer> cartIdColumn;
    @FXML
    public TableColumn<CartTableParameters, LocalDate> dateCreatedColumn;
    @FXML
    public TableColumn<CartTableParameters, Double> cartValueColumn;
    @FXML
    public TableColumn<CartTableParameters, Integer> userIdColumn;
    @FXML
    public TableColumn<CartTableParameters, Boolean> completedColumn;
    @FXML
    public TableColumn<CartTableParameters, Void> cartDummyColumn;
    @FXML
    public Tab statisticsTab;
    @FXML
    public TableView cartData;
    @FXML
    public TableColumn<CartStatisticsTableParameters, Integer> buyerColumn;
    @FXML
    public TableColumn<CartStatisticsTableParameters, LocalDate> dateColumn;
    @FXML
    public TableColumn<CartStatisticsTableParameters, Double> valueColumn;
    @FXML
    public TableColumn<CartStatisticsTableParameters, Integer> ownerColumn;
    @FXML
    public ListView priceView;
    @FXML
    public TextField minCostField;
    @FXML
    public TextField maxCostField;
    @FXML
    public TextField userIdField;
    @FXML
    public DatePicker fromField;
    @FXML
    public DatePicker toField;
    @FXML
    public PieChart cartValuePieChart;
    @FXML
    public TreeView commentTreeView;
    @FXML
    public TableColumn<CartTableParameters, Manager> cartManagerIdColumn;
    @FXML
    public TableColumn<CartTableParameters, String> cartStatusColumn;
    @FXML
    public TableColumn<CartTableParameters, Void> cartManageOrderColumn;


    @FXML
    private ColorPicker mainColorPicker;
    private final ObservableList<CustomerTableParameters> customerTableParametersObservableList = FXCollections.observableArrayList();
    private final ObservableList<ManagerTableParameters> managerTableParametersObservableList = FXCollections.observableArrayList();
    private final ObservableList<CartTableParameters> cartTableParametersObservableList = FXCollections.observableArrayList();
    private final ObservableList<CartStatisticsTableParameters> cartStatisticsTableParametersObservableList = FXCollections.observableArrayList();

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


    private void limitAccess() {
        if (currentUser.getClass() == Manager.class) {
            Manager manager = (Manager) currentUser;
            if (!manager.isAdministrator()) {
                managerTable.setVisible(false);
                adminRegister.setDisable(true);
                customerTable.setEditable(false);
            }
            tabPane.getTabs().remove(primaryTab);
        } else if (currentUser.getClass() == Customer.class) {
            Customer customer = (Customer) currentUser;
            tabPane.getTabs().remove(warehouseTab);
            tabPane.getTabs().remove(usersTab);
            tabPane.getTabs().remove(ordersTab);
            tabPane.getTabs().remove(productTab);
            tabPane.getTabs().remove(statisticsTab);
        } else {
            JavaFxCustomUtils.generateAlert(
                    Alert.AlertType.ERROR,
                    "Access denied",
                    "You have no access to this page",
                    "Please, contact your administrator");
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        productType.getItems().addAll(ProductType.values());
        customerTableParams();
        managerTableParams();
        cartTableParams();
        cartStatisticsTableParams();
        tabPane.getSelectionModel().selectedItemProperty().addListener((obs, oldTab, newTab) -> {
            if (newTab == usersTab) {
                Platform.runLater(() -> {
                    loadUserTables();
                    customerTable.refresh();
                    managerTable.refresh();
                });
            }
        });
    }

    private void managerTableParams() {
        managerTable.setEditable(true);
        managerIdColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        managerUsernameColumn.setCellValueFactory(new PropertyValueFactory<>("username"));
        managerUsernameColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        managerUsernameColumn.setOnEditCommit(event -> {
            event.getTableView().getItems().get(event.getTablePosition().getRow()).setUsername(event.getNewValue());
            Manager manager = customHibernate.readEntityById(Manager.class, event.getTableView().getItems().get(event.getTablePosition().getRow()).getId());
            manager.setUsername(event.getNewValue());
            customHibernate.update(manager);
        });
        managerPasswordColumn.setCellValueFactory(new PropertyValueFactory<>("password"));
        managerNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        managerNameColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        managerNameColumn.setOnEditCommit(event -> {
            event.getTableView().getItems().get(event.getTablePosition().getRow()).setName(event.getNewValue());
            Manager manager = customHibernate.readEntityById(Manager.class, event.getTableView().getItems().get(event.getTablePosition().getRow()).getId());
            manager.setFirstName(event.getNewValue());
            customHibernate.update(manager);
        });
        managerSurnameColumn.setCellValueFactory(new PropertyValueFactory<>("surname"));
        managerSurnameColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        managerSurnameColumn.setOnEditCommit(event -> {
            event.getTableView().getItems().get(event.getTablePosition().getRow()).setSurname(event.getNewValue());
            Manager manager = customHibernate.readEntityById(Manager.class, event.getTableView().getItems().get(event.getTablePosition().getRow()).getId());
            manager.setLastName(event.getNewValue());
            customHibernate.update(manager);
        });
        managerEmployeeIdColumn.setCellValueFactory(new PropertyValueFactory<>("employeeId"));
        managerEmployeeIdColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        managerEmployeeIdColumn.setOnEditCommit(event -> {
            event.getTableView().getItems().get(event.getTablePosition().getRow()).setEmployeeId(event.getNewValue());
            Manager manager = customHibernate.readEntityById(Manager.class, event.getTableView().getItems().get(event.getTablePosition().getRow()).getId());
            manager.setEmployeeId(event.getNewValue());
            customHibernate.update(manager);
        });
        managerMedicalCertificationColumn.setCellValueFactory(new PropertyValueFactory<>("medicalCertification"));
        managerMedicalCertificationColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        managerMedicalCertificationColumn.setOnEditCommit(event -> {
            event.getTableView().getItems().get(event.getTablePosition().getRow()).setMedicalCertification(event.getNewValue());
            Manager manager = customHibernate.readEntityById(Manager.class, event.getTableView().getItems().get(event.getTablePosition().getRow()).getId());
            manager.setMedicalCertification(event.getNewValue());
            customHibernate.update(manager);
        });
        managerIsAdministratorColumn.setCellValueFactory(new PropertyValueFactory<>("isAdministrator"));
        Callback<TableColumn<ManagerTableParameters, Void>, TableCell<ManagerTableParameters, Void>> callback = param -> {
            return new TextFieldTableCell<>() {
                private final Button deleteButton = new Button("Delete");
                {
                    deleteButton.setOnAction(event -> {
                        ManagerTableParameters row = getTableView().getItems().get(getIndex());
                        customHibernate.deleteManager(row.getId());
                        managerTable.getItems().remove(row);
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
        };
        managerDummyColumn.setCellFactory(callback);
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
            return new TextFieldTableCell<>() {
                private final Button deleteButton = new Button("Delete");
                {
                    deleteButton.setOnAction(event -> {
                        CustomerTableParameters row = getTableView().getItems().get(getIndex());
                        customHibernate.deleteCustomer(row.getId());
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
        };
        dummyColumn.setCellFactory(callback);
    }

    private void cartTableParams() {
        cartTable.setEditable(true);
        cartIdColumn.setCellValueFactory(new PropertyValueFactory<>("cartId"));
        dateCreatedColumn.setCellValueFactory(new PropertyValueFactory<>("dateCreated"));
        cartValueColumn.setCellValueFactory(new PropertyValueFactory<>("cartValue"));
        cartValueColumn.setCellFactory(TextFieldTableCell.forTableColumn(new DoubleStringConverter()));
        cartValueColumn.setOnEditCommit(event -> {
            event.getTableView().getItems().get(event.getTablePosition().getRow()).setCartValue(event.getNewValue());
            Cart cart = customHibernate.readEntityById(Cart.class, event.getTableView().getItems().get(event.getTablePosition().getRow()).getCartId());
            cart.setCart_value(event.getNewValue());
            customHibernate.update(cart);
        });
        userIdColumn.setCellValueFactory(new PropertyValueFactory<>("ownerId"));
        cartStatusColumn.setCellValueFactory(new PropertyValueFactory<>("cartStatus"));
        cartStatusColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        cartStatusColumn.setOnEditCommit(event -> {
            event.getTableView().getItems().get(event.getTablePosition().getRow()).setCartStatus(event.getNewValue());
            Cart cart = customHibernate.readEntityById(Cart.class, event.getTableView().getItems().get(event.getTablePosition().getRow()).getCartId());
            cart.setCartStatus(event.getNewValue());
            customHibernate.update(cart);
        });
        cartManagerIdColumn.setCellValueFactory(new PropertyValueFactory<>("cartManagerId"));
        Callback<TableColumn<CartTableParameters, Void>, TableCell<CartTableParameters, Void>> callback = param -> {
            return new TextFieldTableCell<>() {
                private final Button deleteButton = new Button("Delete");
                {
                    deleteButton.setOnAction(event -> {
                        CartTableParameters row = getTableView().getItems().get(getIndex());
                        customHibernate.deleteCart(row.getCartId());
                        cartTable.getItems().remove(row);
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
        };
        cartDummyColumn.setCellFactory(callback);


        cartManageOrderColumn.setCellFactory(param -> new TableCell<CartTableParameters, Void>() {
            private final Button manageButton = new Button("Manage");

            {
                manageButton.setOnAction(event -> {
                    CartTableParameters row = getTableView().getItems().get(getIndex());
                    manageCart(row.getCartId());
                });
            }

            @Override
            public void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setGraphic(null);
                } else {
                    setGraphic(manageButton);
                }
            }
        });


    }

    private void manageCart(int cartId) {
        Cart cart = customHibernate.readEntityById(Cart.class, cartId);
        if (cart != null && currentUser instanceof Manager) {
            cart.setManager((Manager) currentUser);
            customHibernate.update(cart);
            JavaFxCustomUtils.generateAlert(Alert.AlertType.INFORMATION, "Cart Managed", "You are now managing this cart.", "");
        } else {
            JavaFxCustomUtils.generateAlert(Alert.AlertType.ERROR, "Error", "Unable to manage cart.", "Please try again.");
        }
    }

    private void cartStatisticsTableParams() {
        buyerColumn.setCellValueFactory(new PropertyValueFactory<>("buyerId"));
        dateColumn.setCellValueFactory(new PropertyValueFactory<>("date"));
        valueColumn.setCellValueFactory(new PropertyValueFactory<>("cartValue"));
        ownerColumn.setCellValueFactory(new PropertyValueFactory<>("ownerId"));
    }

    public void loadTabValues() {
        if (productTab.isSelected()) {
            disableAllOnTabSelect();
            loadProductListManager();
            warehouseComboBox.getItems().clear();
            warehouseComboBox.getItems().addAll(customHibernate.readAllRecords(Warehouse.class));
        } else if (warehouseTab.isSelected()) {
            loadWarehouseList();
        } else if (commentTab.isSelected()) {
            loadCommentTree();
        } else if (usersTab.isSelected()) {
            loadUserTables();
            customerTable.refresh();
            managerTable.refresh();
        } else if (primaryTab.isSelected()) {
            loadProductList();
        } else if (ordersTab.isSelected()) {
            getAllCarts();
        } else if (statisticsTab.isSelected()) {
            loadCartStatistics();
        }

    }

    private void loadCartStatistics() {
        List<Cart> carts = customHibernate.readAllRecords(Cart.class);
        cartStatisticsTableParametersObservableList.clear();

        for (Cart cart : carts) {
            CartStatisticsTableParameters params = new CartStatisticsTableParameters(
                    cart.getOwnerId(),
                    cart.getDateCreated(),
                    cart.getCartValue(),
                    cart.getOwnerId());
            cartStatisticsTableParametersObservableList.add(params);
        }

        cartData.setItems(cartStatisticsTableParametersObservableList);
    }



    private void loadProductList() {
        productList.getItems().clear();
        List<Product> allProducts = customHibernate.readAllRecords(Product.class);
        productList.getItems().addAll(allProducts);
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

        managerTable.getItems().clear();
        List<Manager> managerList = customHibernate.readAllRecords(Manager.class);
        for (Manager manager : managerList) {
            ManagerTableParameters managerTableParameters = new ManagerTableParameters();
            managerTableParameters.setId(manager.getId());
            managerTableParameters.setUsername(manager.getUsername());
            managerTableParameters.setPassword(manager.getPassword());
            managerTableParameters.setName(manager.getFirstName());
            managerTableParameters.setSurname(manager.getLastName());
            managerTableParameters.setEmployeeId(manager.getEmployeeId());
            managerTableParameters.setMedicalCertification(manager.getMedicalCertification());
            managerTableParameters.setIsAdmin(manager.isAdministrator());
            managerTableParametersObservableList.add(managerTableParameters);
        }
        managerTable.setItems(managerTableParametersObservableList);
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

    public void disableAllOnTabSelect() {
        socketField.setDisable(true);
        chipsetField.setDisable(true);
        coreCountField.setDisable(true);
        coreFrequencyField.setDisable(true);
        tdpField.setDisable(true);
        memoryTypeField.setDisable(true);
        memorySizeField.setDisable(true);
        memoryFrequencyField.setDisable(true);
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
        customHibernate.deleteWarehouse(selectedWarehouse.getId());
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
            customHibernate.create(new CentralProcessingUnit(productTitleField.getText(), productDescriptionField.getText(), productManufacturerField.getText(), customHibernate.readEntityById(Warehouse.class, selectedWarehouse.getId()), Double.parseDouble(productPriceField.getText()), socketField.getText(), Integer.parseInt(coreCountField.getText()), Integer.parseInt(coreFrequencyField.getText()), Integer.parseInt(tdpField.getText())));
        } else if (productType.getSelectionModel().getSelectedItem() == ProductType.MOTHERBOARD) {
            Warehouse selectedWarehouse = warehouseComboBox.getSelectionModel().getSelectedItem();
            customHibernate.create(new Motherboard(productTitleField.getText(), productDescriptionField.getText(), productManufacturerField.getText(), customHibernate.readEntityById(Warehouse.class, selectedWarehouse.getId()), Double.parseDouble(productPriceField.getText()), socketField.getText(), chipsetField.getText(), memoryTypeField.getText(), Integer.parseInt(memorySizeField.getText()), Integer.parseInt(memoryFrequencyField.getText())));
        } else if (productType.getSelectionModel().getSelectedItem() == ProductType.GRAPHICS_CARD) {
            Warehouse selectedWarehouse = warehouseComboBox.getSelectionModel().getSelectedItem();
            customHibernate.create(new GraphicsCard(productTitleField.getText(), productDescriptionField.getText(), productManufacturerField.getText(), customHibernate.readEntityById(Warehouse.class, selectedWarehouse.getId()), Double.parseDouble(productPriceField.getText()), memoryTypeField.getText(), Integer.parseInt(memorySizeField.getText()), Integer.parseInt(memoryFrequencyField.getText()), Integer.parseInt(coreFrequencyField.getText()), Integer.parseInt(tdpField.getText())));
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
        TreeItem<Comment> selectedComment = (TreeItem<Comment>) commentTreeView.getSelectionModel().getSelectedItem();
        if (selectedComment == null)
            customHibernate.create(new Comment(commentTitleField.getText(), commentBodyField.getText(), this.currentUser));
        else
            customHibernate.create(new Comment(commentTitleField.getText(), commentBodyField.getText(), selectedComment.getValue(), this.currentUser));
        loadCommentTree();
    }

    public void updateExistingComment() {
        TreeItem<Comment> selectedComment = (TreeItem<Comment>) commentTreeView.getSelectionModel().getSelectedItem();
        Comment comment = customHibernate.readEntityById(Comment.class, selectedComment.getValue().getId());

        if (canModifyComment(comment, currentUser)) {
            comment.setCommentTitle(commentTitleField.getText());
            comment.setCommentBody(commentBodyField.getText());
            customHibernate.update(comment);
            loadCommentTree();
        } else {
            JavaFxCustomUtils.generateAlert(Alert.AlertType.ERROR, "Access denied", "You have no access to this comment", "Please, contact your administrator");
        }
    }

    public void deleteExistingComment() {
        TreeItem<Comment> selectedComment = (TreeItem<Comment>) commentTreeView.getSelectionModel().getSelectedItem();
        Comment comment = customHibernate.readEntityById(Comment.class, selectedComment.getValue().getId());

        if (canModifyComment(comment, currentUser)) {
            customHibernate.deleteComment(selectedComment.getValue().getId());
            loadCommentTree();
        } else {
            JavaFxCustomUtils.generateAlert(Alert.AlertType.ERROR, "Access denied", "You have no access to this comment", "Please, contact your administrator");
        }
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


    public void exitShop() {
        System.exit(0);
    }

    public void changeMainColor() {
        String hexColor = toHexString(mainColorPicker.getValue());
        mainAnchorPane.setStyle("-fx-background-color: #" + hexColor);
    }


    private String toHexString(Color color) {
        return String.format("%02X%02X%02X",
                (int) (color.getRed() * 255),
                (int) (color.getGreen() * 255),
                (int) (color.getBlue() * 255));
    }

    @FXML
    protected void addToCart() {
        Product selectedProduct = productList.getSelectionModel().getSelectedItem();

        if (selectedProduct != null) {
            currentItemsInCartList.getItems().add(selectedProduct);
            updateTotalAmount();

        } else {
            JavaFxCustomUtils.generateAlert(Alert.AlertType.ERROR, "No Selection", "No product selected to add to the cart.", "Please select a product to add to the cart.");
        }
    }

    @FXML
    protected void removeFromCart() {
        Product selectedProduct = currentItemsInCartList.getSelectionModel().getSelectedItem();


        if (selectedProduct != null) {

            currentItemsInCartList.getItems().remove(selectedProduct);
            updateTotalAmount();
        } else {
            JavaFxCustomUtils.generateAlert(Alert.AlertType.ERROR, "No Selection", "No product selected to remove from the cart.", "Please select a product to remove from the cart.");
        }
    }

    private void updateTotalAmount() {
        double totalAmount = currentItemsInCartList.getItems().stream().mapToDouble(Product::getPrice).sum();
        totalPriceLabel.setText(String.format("%.2f", totalAmount));
    }


    public void addProductsToCart() {
        EntityManager em = null;
        try {
            em = customHibernate.getEntityManager();
            em.getTransaction().begin();
            ObservableList<Product> productsInOrder = currentItemsInCartList.getItems();
            if (productsInOrder.isEmpty()) {
                JavaFxCustomUtils.generateAlert(Alert.AlertType.ERROR, "Error", "No products in the cart", "Please add products to the cart");
                return;
            }
            Cart newCart = new Cart();
            newCart.setDateCreated(LocalDate.now());
            newCart.setOwner(currentUser);
            newCart.setItemsInCart(new ArrayList<>());
            newCart.setManager(null);
            double totalValue = 0.0;
            for (Product product : productsInOrder) {
                Product managedProduct = em.merge(product);
                managedProduct.setCart(newCart);
                newCart.getItemsInCart().add(managedProduct);
                totalValue += managedProduct.getPrice();
            }

            newCart.setCart_value(totalValue);

            em.persist(newCart);

            em.getTransaction().commit();
            currentItemsInCartList.getItems().clear();

        } catch (Exception e) {

            if (em != null) {
                em.getTransaction().rollback();
            }
            JavaFxCustomUtils.generateAlert(Alert.AlertType.ERROR, "Error", "Error occurred while saving the order", "Please try again");
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void adminRegisterNewUser() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(StartGui.class.getResource("registration.fxml"));
        Parent parent = fxmlLoader.load();
        RegistrationController registrationController = fxmlLoader.getController();
        registrationController.setData(entityManagerFactory, true);
        Scene scene = new Scene(parent);
        Stage stage = (Stage) socketField.getScene().getWindow();
        stage.setTitle("Registration");
        stage.setScene(scene);
        stage.show();
    }

    public void getAllCarts() {
        List<Cart> carts;
        if (currentUser instanceof Customer) {
            carts = customHibernate.getAllCartsForSpecificUser(currentUser.getId());
        } else {
            carts = customHibernate.readAllRecords(Cart.class);
        }

        List<CartTableParameters> cartTableParams = carts.stream()
                .map(cart -> new CartTableParameters(
                        cart.getId(),
                        cart.getDateCreated(),
                        cart.getCartValue(),
                        cart.getOwnerId(),
                        cart.getCartStatus(),
                        cart.getManager() != null ? cart.getManager().getId() : -1)
                ).collect(Collectors.toList());

        cartTableParametersObservableList.setAll(cartTableParams);
        cartTable.setItems(cartTableParametersObservableList);
    }


    public void filterData() {
        double minCost = 0.0;
        double maxCost = Double.MAX_VALUE;
        int userId = -1;
        LocalDate fromDate = null;
        LocalDate toDate = null;

        try {
            if (!minCostField.getText().isEmpty()) {
                minCost = Double.parseDouble(minCostField.getText());
            }
            if (!maxCostField.getText().isEmpty()) {
                maxCost = Double.parseDouble(maxCostField.getText());
            }
            if (!userIdField.getText().isEmpty()) {
                userId = Integer.parseInt(userIdField.getText());
            }

            fromDate = fromField.getValue();
            toDate = toField.getValue();

            List<Cart> filteredData = customHibernate.filterData(minCost, maxCost, userId, fromDate, toDate);

        cartData.getItems().clear();
        cartData.getItems().addAll(filteredData);
        double sumOfValues = filteredData.stream()
                .mapToDouble(Cart::getCart_value)
                .sum();

        priceView.getItems().clear();
        priceView.getItems().add("Total Cart Value: " + String.format("%.2f", sumOfValues));

        Map<LocalDate, Double> summedValuesByDate = filteredData.stream()
                .collect(Collectors.groupingBy(
                        Cart::getDateCreated,
                        Collectors.summingDouble(Cart::getCart_value)));

        cartValuePieChart.getData().clear();

        summedValuesByDate.forEach((date, sum) -> {
            PieChart.Data slice = new PieChart.Data(date.toString(), sum);
            cartValuePieChart.getData().add(slice);
        });
    } catch (NumberFormatException e) {
        JavaFxCustomUtils.generateAlert(Alert.AlertType.ERROR, "Error", "Error occurred while filtering data", "Please check your input");

    }
    }

    public void leaveReview() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(StartGui.class.getResource("commentTree.fxml"));
        Parent parent = fxmlLoader.load();
        CommentTreeController commentTree = fxmlLoader.getController();
        commentTree.setData(customHibernate, currentUser);
        var stage = new Stage();
        if (primaryTab.isSelected()) {
            stage = (Stage) productList.getScene().getWindow();
        } else if (productTab.isSelected()) {
            stage = (Stage) productListManager.getScene().getWindow();
        }
        Scene scene = new Scene(parent);
        stage.setTitle("Shop");
        stage.setScene(scene);
        stage.show();
    }

    private void loadCommentTree() {
        List<Comment> comments = customHibernate.readAllRootComments();
        commentTreeView.setRoot(new TreeItem<>());
        commentTreeView.setShowRoot(false);
        commentTreeView.getRoot().setExpanded(true);
        comments.stream()
                .filter(comment -> !(comment instanceof Review))
                .forEach(comment -> addTreeItem(comment, commentTreeView.getRoot()));
    }

    private void addTreeItem(Comment comment, TreeItem<Comment> parentComment) {
        TreeItem<Comment> treeItem = new TreeItem<>(comment);
        parentComment.getChildren().add(treeItem);
        comment.getReplies().stream()
                .filter(reply -> !(reply instanceof Review))
                .forEach(sub -> addTreeItem(sub, treeItem));
    }

    public void showCommentInfo() {
        TreeItem<Comment> selectedComment = (TreeItem<Comment>) commentTreeView.getSelectionModel().getSelectedItem();
        if (selectedComment != null) {
            commentTitleField.setText(selectedComment.getValue().getCommentTitle());
            commentBodyField.setText(selectedComment.getValue().getCommentBody());
        }
    }
}

