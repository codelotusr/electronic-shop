package com.coursework.eshop.fxController.tableViews;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class UserTableParameters {
    SimpleIntegerProperty id = new SimpleIntegerProperty();
    SimpleStringProperty username = new SimpleStringProperty();
    SimpleStringProperty password = new SimpleStringProperty();
    SimpleStringProperty name = new SimpleStringProperty();
    SimpleStringProperty surname = new SimpleStringProperty();

    public UserTableParameters(int id, String username, String password, String firstName, String lastName) {
        this.id.set(id);
        this.username.set(username);
        this.password.set(password);
        this.name.set(firstName);
        this.surname.set(lastName);
    }

    public UserTableParameters() {
    }

    public int getId() {
        return id.get();
    }

    public SimpleIntegerProperty idProperty() {
        return id;
    }

    public void setId(int id) {
        this.id.set(id);
    }

    public String getUsername() {
        return username.get();
    }

    public SimpleStringProperty usernameProperty() {
        return username;
    }

    public void setUsername(String username) {
        this.username.set(username);
    }

    public String getPassword() {
        return password.get();
    }

    public SimpleStringProperty passwordProperty() {
        return password;
    }

    public void setPassword(String password) {
        this.password.set(password);
    }

    public String getName() {
        return name.get();
    }

    public SimpleStringProperty nameProperty() {
        return name;
    }

    public void setName(String name) {
        this.name.set(name);
    }

    public String getSurname() {
        return surname.get();
    }

    public SimpleStringProperty surnameProperty() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname.set(surname);
    }
}
