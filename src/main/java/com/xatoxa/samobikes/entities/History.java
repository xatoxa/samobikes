package com.xatoxa.samobikes.entities;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "repair_history")
public class History {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "user_id")
    private Integer userId;

    @Transient
    private String username;

    @Column(name = "bike_id")
    private Integer bikeId;

    @Transient
    private String number;

    @Transient
    private String qrNumber;

    @Transient
    private String VIN;

    @Column(name = "type")
    private String type;

    @Column(name = "date_point")
    private LocalDateTime datePoint;

    public History() {
    }

    public History(Integer userId, Integer bikeId, String type, LocalDateTime datePoint) {
        this.userId = userId;
        this.bikeId = bikeId;
        this.type = type;
        this.datePoint = datePoint;
    }

    public History(Integer userId, String username, Integer bikeId, String number, String qrNumber, String VIN, String type, LocalDateTime datePoint) {
        this.userId = userId;
        this.username = username;
        this.bikeId = bikeId;
        this.number = number;
        this.qrNumber = qrNumber;
        this.VIN = VIN;
        this.type = type;
        this.datePoint = datePoint;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Integer getBikeId() {
        return bikeId;
    }

    public void setBikeId(Integer bikeId) {
        this.bikeId = bikeId;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getQrNumber() {
        return qrNumber;
    }

    public void setQrNumber(String qrNumber) {
        this.qrNumber = qrNumber;
    }

    public String getVIN() {
        return VIN;
    }

    public void setVIN(String VIN) {
        this.VIN = VIN;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public LocalDateTime getDatePoint() {
        return datePoint;
    }

    public void setDatePoint(LocalDateTime datePoint) {
        this.datePoint = datePoint;
    }

}
