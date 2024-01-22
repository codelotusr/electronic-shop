package com.coursework.eshop.fxController.tableViews;

import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;

import java.time.LocalDate;

public class CartTableParameters {
    SimpleIntegerProperty cartId = new SimpleIntegerProperty();
    SimpleObjectProperty<LocalDate> dateCreated = new SimpleObjectProperty<>();
    SimpleDoubleProperty cartValue = new SimpleDoubleProperty();
    SimpleIntegerProperty ownerId = new SimpleIntegerProperty();
    SimpleBooleanProperty isCompleted = new SimpleBooleanProperty();

    public CartTableParameters(int cartId, LocalDate dateCreated, double cartValue, int ownerId, boolean isCompleted) {
        this.cartId.set(cartId);
        this.dateCreated.set(dateCreated);
        this.cartValue.set(cartValue);
        this.ownerId.set(ownerId);
        this.isCompleted.set(isCompleted);
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

    public boolean isIsCompleted() {
        return isCompleted.get();
    }

    public SimpleBooleanProperty isCompletedProperty() {
        return isCompleted;
    }

    public void setIsCompleted(boolean isCompleted) {
        this.isCompleted.set(isCompleted);
    }


}
