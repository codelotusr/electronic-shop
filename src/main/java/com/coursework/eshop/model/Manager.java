package com.coursework.eshop.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Manager extends User {

    private String employeeId;
    private String medicalCertification;
    private LocalDate employmentDate;
    private boolean isAdministrator;
    public Manager(String username, String password, LocalDate birthDate, String firstName, String lastName) {
        super(username, password, birthDate, firstName, lastName);
    }

}
