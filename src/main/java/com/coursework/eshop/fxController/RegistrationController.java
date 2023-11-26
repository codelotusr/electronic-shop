package com.coursework.eshop.fxController;

import com.coursework.eshop.hibernateController.UserHibernate;
import com.coursework.eshop.model.Customer;
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

    public void setData(EntityManagerFactory entityManagerFactory) {
        this.entityManagerFactory = entityManagerFactory;
        userHibernate = new UserHibernate(entityManagerFactory);
    }

    public void createUser() {
        // TO-DO: create user
        userHibernate.createUser(new Customer());
    }
}
