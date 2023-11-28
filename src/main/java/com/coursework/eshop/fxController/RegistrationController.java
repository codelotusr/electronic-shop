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

    public void createUser() {
        customHibernate = new CustomHibernate(entityManagerFactory);
        if (customerCheckBox.isSelected()) {
            customHibernate.create(new Customer(loginField.getText(), passwordField.getText(), birthDateField.getValue(), nameField.getText(), surnameField.getText(), addressField.getText(), cardNoField.getText()));
        } else if (managerCheckBox.isSelected()) {
            customHibernate.create(new Manager(loginField.getText(), passwordField.getText(), birthDateField.getValue(), nameField.getText(), surnameField.getText(), employeeIdField.getText(), medicalCertificateField.getText(), employmentDateField.getValue(), isAdministratorCheck.isSelected()));
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
}
