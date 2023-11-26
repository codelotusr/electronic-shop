package com.coursework.eshop.model;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Manager extends User {

    private String employeeId;
    private String medicalCertification;
    private LocalDate employmentDate;
    private boolean isAdministrator;
    @ManyToOne
    private Warehouse worksInWarehouse;
    public Manager(String username, String password, LocalDate birthDate, String firstName, String lastName, String employeeId, String medicalCertification, LocalDate employmentDate, boolean isAdministrator) {
        super(username, password, birthDate, firstName, lastName);
        this.employeeId = employeeId;
        this.medicalCertification = medicalCertification;
        this.employmentDate = employmentDate;
        this.isAdministrator = isAdministrator;
    }

}
