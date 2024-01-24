package com.coursework.eshop.fxController.tableViews;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;

import java.time.LocalDate;

public class CartStatisticsTableParameters {
    SimpleIntegerProperty customerId = new SimpleIntegerProperty();
    SimpleObjectProperty<LocalDate> date = new SimpleObjectProperty<>();
    SimpleDoubleProperty cartValue = new SimpleDoubleProperty();

    public CartStatisticsTableParameters(int customerId, LocalDate date, double cartValue) {
        this.customerId.set(customerId);
        this.date.set(date);
        this.cartValue.set(cartValue);
    }

    public CartStatisticsTableParameters() {

    }

    public int getCustomerId() {
        return customerId.get();
    }

    public SimpleIntegerProperty customerIdProperty() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId.set(customerId);
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

}


