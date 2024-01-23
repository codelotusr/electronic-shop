package com.coursework.eshop.fxController.tableViews;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;

import java.time.LocalDate;

public class CartStatisticsTableParameters {
    SimpleIntegerProperty buyerId = new SimpleIntegerProperty();
    SimpleObjectProperty<LocalDate> date = new SimpleObjectProperty<>();
    SimpleDoubleProperty cartValue = new SimpleDoubleProperty();
    SimpleIntegerProperty ownerId = new SimpleIntegerProperty();

    public CartStatisticsTableParameters(int buyerId, LocalDate dates, double cartValue, int ownerId) {
        this.buyerId.set(buyerId);
        this.date.set(dates);
        this.cartValue.set(cartValue);
        this.ownerId.set(ownerId);
    }

    public CartStatisticsTableParameters() {

    }

    public int getBuyerId() {
        return buyerId.get();
    }

    public SimpleIntegerProperty buyerIdProperty() {
        return buyerId;
    }

    public void setBuyerId(int buyerId) {
        this.buyerId.set(buyerId);
    }

    public LocalDate getDate() {
        return date.get();
    }

    public SimpleObjectProperty<LocalDate> dateProperty() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date.set(date);
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


}
