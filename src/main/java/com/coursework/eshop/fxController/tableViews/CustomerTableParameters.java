package com.coursework.eshop.fxController.tableViews;

import javafx.beans.property.SimpleStringProperty;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

public class CustomerTableParameters extends UserTableParameters {
    SimpleStringProperty address = new SimpleStringProperty();
    SimpleStringProperty cardNo = new SimpleStringProperty();

    public CustomerTableParameters(int id, String username, String password, String firstName, String lastName, String address, String cardNo) {
        super(id, username, password, firstName, lastName);
        this.address.set(address);
        this.cardNo.set(cardNo);
    }

    public CustomerTableParameters() {

    }


    public String getAddress() {
        return address.get();
    }

    public SimpleStringProperty addressProperty() {
        return address;
    }

    public void setAddress(String address) {
        this.address.set(address);
    }

    public String getCardNo() {
        return cardNo.get();
    }

    public SimpleStringProperty cardNoProperty() {
        return cardNo;
    }

    public void setCardNo(String cardNumber) {
        this.cardNo.set(cardNumber);
    }
}
