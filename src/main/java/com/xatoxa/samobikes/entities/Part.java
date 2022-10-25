package com.xatoxa.samobikes.entities;

import javax.persistence.*;

@Entity
@Table(name = "parts")
public class Part {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "name")
    private String name;

    @Column(name = "importance")
    private int importance;

    @Column(name = "description")
    private String description;

    @Column(name = "status")
    private boolean status;

    @ManyToOne
    @JoinColumn(name = "bike_id")
    private Bike bike;

    public Part() {
    }

    public Part(String name, int importance, String description, boolean status, Bike bike) {
        this.name = name;
        this.importance = importance;
        this.description = description;
        this.status = status;
        this.bike = bike;
    }

    public Part(Integer id, String name, int importance, String description, boolean status, Bike bike) {
        this.id = id;
        this.name = name;
        this.importance = importance;
        this.description = description;
        this.status = status;
        this.bike = bike;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getImportance() {
        return importance;
    }

    public void setImportance(int importance) {
        this.importance = importance;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public Bike getBike() {
        return bike;
    }

    public void setBike(Bike bike) {
        this.bike = bike;
    }

    @Transient
    public String getPhotoImagePath(){
        if (this.id == null || this.description == null || this.description.equals("")) return "/img/logo.png";
        return "/photos/part-photos/" + this.description;
    }
}
