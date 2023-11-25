package com.coursework.eshop.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Processor extends Product {
    private String socket;
    private String core;
    private String coreFrequency;
    private String turboFrequency;
    private String techProcess;
    private String tdp;
    private String integratedGraphics;
    private String cooler;

    public Processor(int id, String name, String description, String socket, String core, String coreFrequency, String turboFrequency, String techProcess, String tdp, String integratedGraphics, String cooler) {
        super(id, name, description);
        this.socket = socket;
        this.core = core;
        this.coreFrequency = coreFrequency;
        this.turboFrequency = turboFrequency;
        this.techProcess = techProcess;
        this.tdp = tdp;
        this.integratedGraphics = integratedGraphics;
        this.cooler = cooler;
    }

    @Override
    public String toString() {
        return "Processor{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                '}';
    }


}
