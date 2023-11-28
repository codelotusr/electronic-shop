package com.coursework.eshop.fxController;

import com.coursework.eshop.StartGui;
import com.coursework.eshop.hibernateController.CustomHibernate;
import com.coursework.eshop.model.Customer;
import com.coursework.eshop.model.Manager;
import jakarta.persistence.EntityManagerFactory;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;

public class RegistrationController {

    @FXML
    public TextField loginField;
    @FXML
    public PasswordField repeatPasswordField;
    @FXML
    public PasswordField passwordField;
    @FXML
    public TextField nameField;
    @FXML
    public TextField surnameField;
    @FXML
    public RadioButton customerCheckBox;
    @FXML
    public ToggleGroup userType;
    @FXML
    public RadioButton managerCheckBox;
    @FXML
    public DatePicker birthDateField;
    @FXML
    public TextField employeeIdField;
    @FXML
    public TextField medicalCertificateField;
    @FXML
    public DatePicker employmentDateField;
    @FXML
    public CheckBox isAdministratorCheck;
    @FXML
    public TextField addressField;
    @FXML
    public TextField cardNoField;

    private EntityManagerFactory entityManagerFactory;

    private CustomHibernate customHibernate;

    public void setData(EntityManagerFactory entityManagerFactory, boolean showManagerFields) {
        this.entityManagerFactory = entityManagerFactory;
        disableFields(showManagerFields);
    }

    public void setData(EntityManagerFactory entityManagerFactory, Manager manager) {
        this.entityManagerFactory = entityManagerFactory;
        toggleFields(customerCheckBox.isSelected(), manager);
    }



    private void disableFields(boolean showManagerFields) {
        if (!showManagerFields) {
            employeeIdField.setVisible(false);
            medicalCertificateField.setVisible(false);
            employmentDateField.setVisible(false);
            isAdministratorCheck.setVisible(false);
            managerCheckBox.setVisible(false);
            customerCheckBox.setSelected(true);
            customerCheckBox.setVisible(false);
        }
    }

    private void toggleFields(boolean isManager, Manager manager) {
        if (isManager) {
            addressField.setDisable(true);
            cardNoField.setDisable(true);
            employeeIdField.setDisable(false);
            medicalCertificateField.setDisable(false);
            employmentDateField.setDisable(false);
            if (manager.isAdministrator()) isAdministratorCheck.setDisable(false);
        } else {
            addressField.setDisable(false);
            cardNoField.setDisable(false);
            employeeIdField.setDisable(true);
            medicalCertificateField.setDisable(true);
            employmentDateField.setDisable(true);
            isAdministratorCheck.setDisable(true);
        }
    }

    public void createUser() throws IOException {
        customHibernate = new CustomHibernate(entityManagerFactory);
        if (customerCheckBox.isSelected() && validateInputCustomer()) {
            customHibernate.create(new Customer(loginField.getText(), passwordField.getText(), birthDateField.getValue(), nameField.getText(), surnameField.getText(), addressField.getText(), cardNoField.getText()));
            returnToLogin();
        } else if (managerCheckBox.isSelected() && validateInputManager()) {
            customHibernate.create(new Manager(loginField.getText(), passwordField.getText(), birthDateField.getValue(), nameField.getText(), surnameField.getText(), employeeIdField.getText(), medicalCertificateField.getText(), employmentDateField.getValue(), isAdministratorCheck.isSelected()));
            returnToLogin();
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("User type not selected");
            alert.setContentText("Please select user type");
            alert.showAndWait();
        }
    }

    public void returnToLogin() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(StartGui.class.getResource("login.fxml"));
        Parent parent = fxmlLoader.load();
        LoginController loginController = fxmlLoader.getController();
        loginController.setData(entityManagerFactory);
        Scene scene = new Scene(parent);
        Stage stage = (Stage) loginField.getScene().getWindow();
        stage.setTitle("Login");
        stage.setScene(scene);
        stage.show();
    }

    private boolean validateInputCustomer() {
        boolean isValid = true;
        if (loginField.getText().isEmpty()) {
            JavaFxCustomUtils.generateAlert(
                    Alert.AlertType.ERROR,
                    "Error",
                    "Error",
                    "Username field is empty"
            );
            isValid = false;
        }
        if (passwordField.getText().isEmpty()) {
            isValid = false;
            JavaFxCustomUtils.generateAlert(
                    Alert.AlertType.ERROR,
                    "Error",
                    "Error",
                    "Password field is empty"
            );
        }
        if (repeatPasswordField.getText().isEmpty()) {
            isValid = false;
            JavaFxCustomUtils.generateAlert(
                    Alert.AlertType.ERROR,
                    "Error",
                    "Error",
                    "Repeat password field is empty"
            );
        }
        if (nameField.getText().isEmpty()) {
            isValid = false;
            JavaFxCustomUtils.generateAlert(
                    Alert.AlertType.ERROR,
                    "Error",
                    "Error",
                    "Name field is empty"
            );
        }
        if (surnameField.getText().isEmpty()) {
            isValid = false;
            JavaFxCustomUtils.generateAlert(
                    Alert.AlertType.ERROR,
                    "Error",
                    "Error",
                    "Surname field is empty"
            );
        }
        if (birthDateField.getValue() == null) {
            isValid = false;
            JavaFxCustomUtils.generateAlert(
                    Alert.AlertType.ERROR,
                    "Error",
                    "Error",
                    "Birth date field is empty"
            );
        }
        if (addressField.getText().isEmpty()) {
            isValid = false;
            JavaFxCustomUtils.generateAlert(
                    Alert.AlertType.ERROR,
                    "Error",
                    "Error",
                    "Address field is empty"
            );
        }
        if (cardNoField.getText().isEmpty()) {
            isValid = false;
            JavaFxCustomUtils.generateAlert(
                    Alert.AlertType.ERROR,
                    "Error",
                    "Error",
                    "Card number field is empty"
            );
        }
        if (!passwordField.getText().equals(repeatPasswordField.getText())) {
            isValid = false;
            JavaFxCustomUtils.generateAlert(
                    Alert.AlertType.ERROR,
                    "Error",
                    "Error",
                    "Passwords do not match"
            );
        }
        if (birthDateField.getValue().isAfter(java.time.LocalDate.now())) {
            isValid = false;
            JavaFxCustomUtils.generateAlert(
                    Alert.AlertType.ERROR,
                    "Error",
                    "Error",
                    "Birth date is in the future"
            );
        }
        if (cardNoField.getText().length() != 16) {
            isValid = false;
            JavaFxCustomUtils.generateAlert(
                    Alert.AlertType.ERROR,
                    "Error",
                    "Error",
                    "Card number must be 16 digits long"
            );
        }
        if (!cardNoField.getText().matches("[0-9]+")) {
            isValid = false;
            JavaFxCustomUtils.generateAlert(
                    Alert.AlertType.ERROR,
                    "Error",
                    "Error",
                    "Card number must contain only digits"
            );
        }
        if (!addressField.getText().matches("[a-zA-Z0-9]+")) {
            isValid = false;
            JavaFxCustomUtils.generateAlert(
                    Alert.AlertType.ERROR,
                    "Error",
                    "Error",
                    "Address must contain only letters and digits"
            );
        }
        if (!nameField.getText().matches("[a-zA-Z]+")) {
            isValid = false;
            JavaFxCustomUtils.generateAlert(
                    Alert.AlertType.ERROR,
                    "Error",
                    "Error",
                    "Name must contain only letters"
            );
        }
        if (!surnameField.getText().matches("[a-zA-Z]+")) {
            isValid = false;
            JavaFxCustomUtils.generateAlert(
                    Alert.AlertType.ERROR,
                    "Error",
                    "Error",
                    "Surname must contain only letters"
            );
        }
        if (!loginField.getText().matches("[a-zA-Z0-9]+")) {
            isValid = false;
            JavaFxCustomUtils.generateAlert(
                    Alert.AlertType.ERROR,
                    "Error",
                    "Error",
                    "Username must contain only letters and digits"
            );
        }
        if (!passwordField.getText().matches("[a-zA-Z0-9]+")) {
            isValid = false;
            JavaFxCustomUtils.generateAlert(
                    Alert.AlertType.ERROR,
                    "Error",
                    "Error",
                    "Password must contain only letters and digits"
            );
        }
        return isValid;
    }

    public boolean validateInputManager() {
        boolean isValid = true;
        if (loginField.getText().isEmpty()) {
            JavaFxCustomUtils.generateAlert(
                    Alert.AlertType.ERROR,
                    "Error",
                    "Error",
                    "Username field is empty"
            );
            isValid = false;
        }
        if (passwordField.getText().isEmpty()) {
            isValid = false;
            JavaFxCustomUtils.generateAlert(
                    Alert.AlertType.ERROR,
                    "Error",
                    "Error",
                    "Password field is empty"
            );
        }
        if (repeatPasswordField.getText().isEmpty()) {
            isValid = false;
            JavaFxCustomUtils.generateAlert(
                    Alert.AlertType.ERROR,
                    "Error",
                    "Error",
                    "Repeat password field is empty"
            );
        }
        if (nameField.getText().isEmpty()) {
            isValid = false;
            JavaFxCustomUtils.generateAlert(
                    Alert.AlertType.ERROR,
                    "Error",
                    "Error",
                    "Name field is empty"
            );
        }
        if (surnameField.getText().isEmpty()) {
            isValid = false;
            JavaFxCustomUtils.generateAlert(
                    Alert.AlertType.ERROR,
                    "Error",
                    "Error",
                    "Surname field is empty"
            );
        }
        if (birthDateField.getValue() == null) {
            isValid = false;
            JavaFxCustomUtils.generateAlert(
                    Alert.AlertType.ERROR,
                    "Error",
                    "Error",
                    "Birth date field is empty"
            );
        }
        if (employeeIdField.getText().isEmpty()) {
            isValid = false;
            JavaFxCustomUtils.generateAlert(
                    Alert.AlertType.ERROR,
                    "Error",
                    "Error",
                    "Employee ID field is empty"
            );
        }
        if (medicalCertificateField.getText().isEmpty()) {
            isValid = false;
            JavaFxCustomUtils.generateAlert(
                    Alert.AlertType.ERROR,
                    "Error",
                    "Error",
                    "Medical certificate field is empty"
            );
        }
        if (employmentDateField.getValue() == null) {
            isValid = false;
            JavaFxCustomUtils.generateAlert(
                    Alert.AlertType.ERROR,
                    "Error",
                    "Error",
                    "Employment date field is empty"
            );
        }
        if (!passwordField.getText().equals(repeatPasswordField.getText())) {
            isValid = false;
            JavaFxCustomUtils.generateAlert(
                    Alert.AlertType.ERROR,
                    "Error",
                    "Error",
                    "Passwords do not match"
            );
        }
        if (birthDateField.getValue().isAfter(java.time.LocalDate.now())) {
            isValid = false;
            JavaFxCustomUtils.generateAlert(
                    Alert.AlertType.ERROR,
                    "Error",
                    "Error",
                    "Birth date is in the future"
            );
        }
        if (employmentDateField.getValue().isAfter(java.time.LocalDate.now())) {
            isValid = false;
            JavaFxCustomUtils.generateAlert(
                    Alert.AlertType.ERROR,
                    "Error",
                    "Error",
                    "Employment date is in the future"
            );
        }
        if (employeeIdField.getText().length() != 6) {
            isValid = false;
            JavaFxCustomUtils.generateAlert(
                    Alert.AlertType.ERROR,
                    "Error",
                    "Error",
                    "Employee ID must be 6 digits long"
            );
        }
        if (!employeeIdField.getText().matches("[0-9]+")) {
            isValid = false;
            JavaFxCustomUtils.generateAlert(
                    Alert.AlertType.ERROR,
                    "Error",
                    "Error",
                    "Employee ID must contain only digits"
            );
        }
        if (!medicalCertificateField.getText().matches("[0-9]+")) {
            isValid = false;
            JavaFxCustomUtils.generateAlert(
                    Alert.AlertType.ERROR,
                    "Error",
                    "Error",
                    "Medical certificate must contain only digits"
            );
        }
        if (!nameField.getText().matches("[a-zA-Z]+")) {
            isValid = false;
            JavaFxCustomUtils.generateAlert(
                    Alert.AlertType.ERROR,
                    "Error",
                    "Error",
                    "Name must contain only letters"
            );
        }
        if (!surnameField.getText().matches("[a-zA-Z]+")) {
            isValid = false;
            JavaFxCustomUtils.generateAlert(
                    Alert.AlertType.ERROR,
                    "Error",
                    "Error",
                    "Surname must contain only letters"
            );
        }
        if (!loginField.getText().matches("[a-zA-Z0-9]+")) {
            isValid = false;
            JavaFxCustomUtils.generateAlert(
                    Alert.AlertType.ERROR,
                    "Error",
                    "Error",
                    "Username must contain only letters and digits"
            );
        }
        if (!passwordField.getText().matches("[a-zA-Z0-9]+")) {
            isValid = false;
            JavaFxCustomUtils.generateAlert(
                    Alert.AlertType.ERROR,
                    "Error",
                    "Error",
                    "Password must contain only letters and digits"
            );
        }
        return isValid;
    }
}
