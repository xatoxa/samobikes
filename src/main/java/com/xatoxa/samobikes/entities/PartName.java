package com.xatoxa.samobikes.entities;

import javax.persistence.*;

@Entity
@Table(name = "part_names")
public class PartName {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "name")
    private String name;

    @Column(name = "importance")
    private int importance;

    @Column(name = "description")
    private String description;

    public PartName() {
    }

    public PartName(Integer id, String name) {
        this.id = id;
        this.name = name;
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

    @Transient
    public String getPhotoImagePath(){
        if (this.id == null || this.description == null || "".equals(this.description)) return "/img/logo.png";
        return "/photos/part-photos/" + this.description;
    }
}
