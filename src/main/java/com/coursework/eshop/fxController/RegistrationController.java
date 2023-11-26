package com.coursework.eshop.fxController;

import com.coursework.eshop.hibernateController.UserHibernate;
import com.coursework.eshop.model.Customer;
import com.coursework.eshop.model.Manager;
import jakarta.persistence.EntityManagerFactory;
import javafx.fxml.FXML;
import javafx.scene.control.*;

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
    private UserHibernate userHibernate;

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
        userHibernate = new UserHibernate(entityManagerFactory);
        if (customerCheckBox.isSelected()) {
            userHibernate.createUser(new Customer(loginField.getText(), passwordField.getText(), birthDateField.getValue(), nameField.getText(), surnameField.getText(), addressField.getText(), cardNoField.getText()));
        } else if (managerCheckBox.isSelected()) {
            // TO-DO add manager
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("User type not selected");
            alert.setContentText("Please select user type");
            alert.showAndWait();
        }
    }
}
