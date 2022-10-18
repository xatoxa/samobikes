package com.xatoxa.samobikes.entities;

import java.util.List;

public class PartListDTO {
    private List<Part> parts;

    private String params;

    private int bikeId;

    public List<Part> getParts() {
        return parts;
    }

    public void setParts(List<Part> parts) {
        this.parts = parts;
    }

    public String getParams() {
        return params;
    }

    public void setParams(String params) {
        this.params = params;
    }

    public int getBikeId() {
        return bikeId;
    }

    public void setBikeId(int bikeId) {
        this.bikeId = bikeId;
    }
}
