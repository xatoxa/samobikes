package com.xatoxa.samobikes.entities;

import javax.persistence.*;

@Entity
@Table(name = "bikes")
public class Bike {
    @Id
    @Column(name = "id")
    private Integer id;

    @OneToOne(mappedBy = "bike", cascade = CascadeType.ALL)
    private Part part;

    @Column(name = "number")
    private Integer number;

    @Column(name = "VIN")
    private String VIN;

    @Column(name = "status")
    private boolean status;

    @Column(name = "comment")
    private String comment;

    @Column(name = "qr_code")
    private String qr_code;

    //Construct
    public Bike() {
    }

    public Bike(Integer id, Integer number, String VIN, boolean status, String comment, String qr_code) {
        this.id = id;
        this.number = number;
        this.VIN = VIN;
        this.status = status;
        this.comment = comment;
        this.qr_code = qr_code;
    }

    //getters, setters
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }


    public void setPart(Part part) {
        this.part = part;
    }

    public Part getPart() {
        return part;
    }

    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
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

    public String getQr_code() {
        return qr_code;
    }

    public void setQr_code(String qr_code) {
        this.qr_code = qr_code;
    }

    //methods
    public void checkWorks(){
        this.status = this.part.checkWork();
    }
}
