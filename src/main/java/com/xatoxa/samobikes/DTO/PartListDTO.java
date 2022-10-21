package com.xatoxa.samobikes.DTO;

import com.xatoxa.samobikes.entities.Part;

import java.util.List;
import java.util.stream.Stream;

public class PartListDTO {
    private List<Part> partsLeft;

    private List<Part> partsRight;

    private String params;

    private int bikeId;

    public List<Part> getPartsLeft() {
        return partsLeft;
    }

    public void setPartsLeft(List<Part> partsLeft) {
        this.partsLeft = partsLeft;
    }

    public List<Part> getPartsRight() {
        return partsRight;
    }

    public void setPartsRight(List<Part> partsRight) {
        this.partsRight = partsRight;
    }

    public List<Part> getParts(){
        return Stream.concat(partsLeft.stream(), partsRight.stream()).toList();
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
