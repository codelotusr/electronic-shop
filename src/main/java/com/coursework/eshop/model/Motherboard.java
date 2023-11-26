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
    private String maxMemorySize;
    private String maxMemoryFrequency;

    public Motherboard(int id, String title, String description, String socket, String chipset, String memoryType, String maxMemorySize, String maxMemoryFrequency) {
        super(title, description);
        this.socket = socket;
        this.chipset = chipset;
        this.memoryType = memoryType;
        this.maxMemorySize = maxMemorySize;
        this.maxMemoryFrequency = maxMemoryFrequency;
    }

    @Override
    public String toString() {
        return "Motherboard{" +
                "id=" + id +
                ", name='" + title + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}
