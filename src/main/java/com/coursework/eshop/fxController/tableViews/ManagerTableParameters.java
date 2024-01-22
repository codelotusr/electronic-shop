package com.coursework.eshop.fxController.tableViews;

import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;

public class ManagerTableParameters extends UserTableParameters {

    SimpleStringProperty employeeId = new SimpleStringProperty();
    SimpleBooleanProperty isAdmin = new SimpleBooleanProperty();
    SimpleStringProperty medicalCertification = new SimpleStringProperty();

    public String getEmployeeId() {
        return employeeId.get();
    }

    public SimpleStringProperty employeeIdProperty() {
        return employeeId;
    }

    public void setEmployeeId(String employeeId) {
        this.employeeId.set(employeeId);
    }


    public boolean getIsAdministrator() {
        return isAdmin.get();
    }

    public SimpleBooleanProperty isAdminProperty() {
        return isAdmin;
    }

    public void setIsAdmin(boolean isAdmin) {
        this.isAdmin.set(isAdmin);
    }

    public String getMedicalCertification() {
        return medicalCertification.get();
    }

    public SimpleStringProperty medicalCertificationProperty() {
        return medicalCertification;
    }

    public void setMedicalCertification(String medicalCertification) {
        this.medicalCertification.set(medicalCertification);
    }

    public ManagerTableParameters() {

    }
}
