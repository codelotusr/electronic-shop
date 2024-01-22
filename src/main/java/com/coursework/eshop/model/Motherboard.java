package com.coursework.eshop.model;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
public class Motherboard extends Product {
    private String socket;
    private String chipset;
    private String memoryType;
    private int maxMemorySize;
    private int maxMemoryFrequency;

    public Motherboard(String title, String description, String socket, String chipset, String memoryType, int memorySize, int memoryFrequency) {
        super(title, description);
        this.socket = socket;
        this.chipset = chipset;
        this.memoryType = memoryType;
        this.maxMemorySize = memorySize;
        this.maxMemoryFrequency = memoryFrequency;
    }

    public Motherboard(String title, String description, String manufacturer, Warehouse warehouse, double price, String socket, String chipset, String memoryType, int maxMemorySize, int maxMemoryFrequency) {
        super(title, description, manufacturer, warehouse, price);
        this.socket = socket;
        this.chipset = chipset;
        this.memoryType = memoryType;
        this.maxMemorySize = maxMemorySize;
        this.maxMemoryFrequency = maxMemoryFrequency;
    }

}
