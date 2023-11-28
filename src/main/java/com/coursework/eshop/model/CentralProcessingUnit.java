package com.coursework.eshop.model;

import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
public class CentralProcessingUnit extends Product {
    private String socket;
    private int coreCount;
    private int coreFrequency;
    private int tdp;

    public CentralProcessingUnit(String title, String description, String socket, int coreCount, int coreFrequency, int tdp) {
        super(title, description);
        this.socket = socket;
        this.coreCount = coreCount;
        this.coreFrequency = coreFrequency;
        this.tdp = tdp;
    }

    public CentralProcessingUnit(String title, String description, String manufacturer, Warehouse warehouse, String socket, int coreCount, int coreFrequency, int tdp) {
        super(title, description, manufacturer, warehouse);
        this.socket = socket;
        this.coreCount = coreCount;
        this.coreFrequency = coreFrequency;
        this.tdp = tdp;
    }

}
