package com.coursework.eshop.fxController.tableViews;

import javafx.beans.property.*;

import java.time.LocalDate;

public class CartTableParameters {
    SimpleIntegerProperty cartId = new SimpleIntegerProperty();
    SimpleObjectProperty<LocalDate> dateCreated = new SimpleObjectProperty<>();
    SimpleDoubleProperty cartValue = new SimpleDoubleProperty();
    SimpleIntegerProperty ownerId = new SimpleIntegerProperty();
    SimpleStringProperty cartStatus = new SimpleStringProperty();
    SimpleObjectProperty<Integer> cartManagerId = new SimpleObjectProperty<>();

    public CartTableParameters(int cartId, LocalDate dateCreated, double cartValue, int ownerId, String cartStatus, Integer cartManagerId) {
        this.cartId.set(cartId);
        this.dateCreated.set(dateCreated);
        this.cartValue.set(cartValue);
        this.ownerId.set(ownerId);
        this.cartStatus.set(cartStatus);
        this.cartManagerId.set(cartManagerId != null ? cartManagerId : -1);
    }

    public CartTableParameters() {

    }

    public int getCartId() {
        return cartId.get();
    }

    public SimpleIntegerProperty cartIdProperty() {
        return cartId;
    }

    public void setCartId(int cartId) {
        this.cartId.set(cartId);
    }

    public LocalDate getDateCreated() {
        return dateCreated.get();
    }

    public SimpleObjectProperty<LocalDate> dateCreatedProperty() {
        return dateCreated;
    }

    public void setDateCreated(LocalDate dateCreated) {
        this.dateCreated.set(dateCreated);
    }

    public double getCartValue() {
        return cartValue.get();
    }

    public SimpleDoubleProperty cartValueProperty() {
        return cartValue;
    }

    public void setCartValue(double cartValue) {
        this.cartValue.set(cartValue);
    }

    public int getOwnerId() {
        return ownerId.get();
    }

    public SimpleIntegerProperty ownerIdProperty() {
        return ownerId;
    }

    public void setOwnerId(int ownerId) {
        this.ownerId.set(ownerId);
    }

    public String getCartStatus() {
        return cartStatus.get();
    }

    public SimpleStringProperty cartStatusProperty() {
        return cartStatus;
    }

    public void setCartStatus(String cartStatus) {
        this.cartStatus.set(cartStatus);
    }

    public Integer getCartManagerId() {
        return cartManagerId.get();
    }

    public SimpleObjectProperty<Integer> cartManagerIdProperty() {
        return cartManagerId;
    }

    public void setCartManagerId(Integer cartManagerId) {
        this.cartManagerId.set(cartManagerId);
    }




}
