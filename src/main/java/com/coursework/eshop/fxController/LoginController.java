package com.coursework.eshop.fxController;

import com.coursework.eshop.model.Customer;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class LoginController {
    public TextField login;
    public PasswordField password;

    public void check() {
        System.out.println(login.getText() + " " + password.getText());
    }

    public void registerNewUser() {

    }
}