package com.xatoxa.samobikes.entities;

import javax.persistence.*;
import java.util.Collection;

@Entity
@Table(name = "bikes")
public class Bike {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @OneToOne(mappedBy = "bike", cascade = CascadeType.ALL, orphanRemoval = true)
    private Part part;

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

    @Column(name = "comment")
    private String comment;

    @Column(name = "qr_link")
    private String qrLink;

    @Column(name = "photo")
    private String photo;

    //Construct
    public Bike() {
    }

    public Bike(Integer id, Part part, Integer number, Integer qrNumber, String VIN, boolean status, String comment) {
        this.id = id;
        this.part = part;
        this.number = number;
        this.qrNumber = qrNumber;
        this.VIN = VIN;
        this.status = status;
        this.comment = comment;
    }

    public Bike(Integer id, Part part, Integer number, Integer qrNumber, String VIN,
                boolean status, String comment, String qrLink, String photo) {
        this.id = id;
        this.part = part;
        this.number = number;
        this.qrNumber = qrNumber;
        this.VIN = VIN;
        this.status = status;
        this.comment = comment;
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

    public Part getPart() {
        return part;
    }

    public void setPart(Part part) {
        this.part = part;
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

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
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
        this.status = this.part.checkWork();
    }

    public Collection<Comment> getComments() {
        return comments;
    }

    public void setComments(Collection<Comment> comments) {
        this.comments = comments;
    }
}
