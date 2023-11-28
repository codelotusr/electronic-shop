package com.coursework.eshop.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
public class GraphicsCard extends Product {
    private String memoryType;
    private int memorySize;
    private int memoryFrequency;
    private int coreFrequency;
    private int tdp;


    public GraphicsCard(String title, String description, String memoryType, int memorySize, int memoryFrequency, int coreFrequency, int tdp) {
        super(title, description);
        this.memoryType = memoryType;
        this.memorySize = memorySize;
        this.memoryFrequency = memoryFrequency;
        this.coreFrequency = coreFrequency;
        this.tdp = tdp;
    }

    public GraphicsCard(String title, String description, String manufacturer, Warehouse warehouse, String memoryType, int memorySize, int memoryFrequency, int coreFrequency, int tdp) {
        super(title, description, manufacturer, warehouse);
        this.memoryType = memoryType;
        this.memorySize = memorySize;
        this.memoryFrequency = memoryFrequency;
        this.coreFrequency = coreFrequency;
        this.tdp = tdp;
    }

}
