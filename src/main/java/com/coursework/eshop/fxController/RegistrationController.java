package com.coursework.eshop.fxController;

import com.coursework.eshop.utils.DatabaseUtils;
import javafx.event.ActionEvent;
import javafx.scene.control.*;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;

public class RegistrationController {

    public TextField loginField;
    public PasswordField repeatPasswordField;
    public PasswordField passwordField;
    public TextField nameField;
    public TextField surnameField;
    public RadioButton customerCheckBox;
    public ToggleGroup userType;
    public RadioButton managerCheckBox;
    public DatePicker birthDateField;
    public TextField employeeIdField;
    public TextField medicalCertificateField;
    public DatePicker employmentDateField;
    public CheckBox isAdministratorCheck;
    public TextField addressField;
    public TextField cardNoField;

    public void createUser() {
        try {
            Connection connection = DatabaseUtils.connectDb();
            PreparedStatement insertUser = null;
            var sql = "";
            if (managerCheckBox.isSelected()) {
                sql = "INSERT INTO user (login, password, name, surname, birthDate, employeeId, medicalCertificate, employmentDate, isAdministrator, userType) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
                insertUser = connection.prepareStatement(sql);
                insertUser.setString(1, loginField.getText());
                insertUser.setString(2, passwordField.getText());
                insertUser.setString(3, nameField.getText());
                insertUser.setString(4, surnameField.getText());
                insertUser.setDate(5, Date.valueOf(birthDateField.getValue()));
                insertUser.setString(6, employeeIdField.getText());
                insertUser.setString(7, medicalCertificateField.getText());
                insertUser.setDate(8, Date.valueOf(employmentDateField.getValue()));
                insertUser.setBoolean(9, isAdministratorCheck.isSelected());
                insertUser.setString(10, "M");
                insertUser.execute();

            } else {
                sql = "INSERT INTO user (login, password, name, surname, birthDate, address, cardNo, userType) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
                insertUser = connection.prepareStatement(sql);
                insertUser.setString(1, loginField.getText());
                insertUser.setString(2, passwordField.getText());
                insertUser.setString(3, nameField.getText());
                insertUser.setString(4, surnameField.getText());
                insertUser.setDate(5, Date.valueOf(birthDateField.getValue()));
                insertUser.setString(6, addressField.getText());
                insertUser.setString(7, cardNoField.getText());
                insertUser.setString(8, "C");
                insertUser.execute();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }




    }
}
