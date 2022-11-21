package com.xatoxa.samobikes.entities;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Entity
@Table(name = "bikes")
public class Bike {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @OneToMany(mappedBy = "bike", cascade = CascadeType.ALL)
    private List<Part> parts;

    @OneToMany(mappedBy = "bike")
    Collection<Comment> comments;

    @Column(name = "number")
    private Integer number;

    @Column(name = "qr_number")
    private Integer qrNumber;

    @Column(name = "VIN")
    private String VIN;

    @Column(name = "status")
    private boolean status;

    @Column(name = "description")
    private String description;

    @Column(name = "qr_link")
    private String qrLink;

    @Column(name = "photo")
    private String photo;

    //Construct
    public Bike() {
    }

    public Bike(Integer id, List<Part> parts, Integer number, Integer qrNumber, String VIN, boolean status, String description) {
        this.id = id;
        this.parts = parts;
        this.number = number;
        this.qrNumber = qrNumber;
        this.VIN = VIN;
        this.status = status;
        this.description = description;
    }

    public Bike(Integer id, List<Part> parts, Integer number, Integer qrNumber, String VIN,
                boolean status, String description, String qrLink, String photo) {
        this.id = id;
        this.parts = parts;
        this.number = number;
        this.qrNumber = qrNumber;
        this.VIN = VIN;
        this.status = status;
        this.description = description;
        this.qrLink = qrLink;
        this.photo = photo;
    }

    //getters, setters
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public List<Part> getParts() {
        return parts;
    }

    public void setParts(List<Part> parts) {
        this.parts = parts;
    }

    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

    public Integer getQrNumber() {
        return qrNumber;
    }

    public void setQrNumber(Integer qrNumber) {
        this.qrNumber = qrNumber;
    }

    public String getVIN() {
        return VIN;
    }

    public void setVIN(String VIN) {
        this.VIN = VIN;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getQrLink() {
        return qrLink;
    }

    public void setQrLink(String qrLink) {
        this.qrLink = qrLink;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    //methods
    public void checkWorks(){
        for (Part part:
             parts) {
            if (!part.isStatus() && part.getImportance() < 3){
                this.status = false;
                return;
            }
        }
        this.status = true;
    }

    public Collection<Comment> getComments() {
        return comments;
    }

    public void setComments(Collection<Comment> comments) {
        this.comments = comments;
    }

    public void addComment(Comment comment){
        this.comments.add(comment);
    }

    @Transient
    public String getPhotoImagePath(){
        if (this.id == null || this.photo == null || "".equals(this.photo)) return "/img/default-bike.png";
        return "/photos/bike-photos/"  + this.getId() + "/" + this.photo;
    }
}
