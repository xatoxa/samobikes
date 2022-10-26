package com.xatoxa.samobikes.entities;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "repair_history")
public class RepairHistory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "user_id")
    private Integer userId;

    @Column(name = "bike_id")
    private Integer bikeId;

    @Column(name = "breakdown_date")
    private LocalDateTime breakdownDate;

    @Column(name = "repair_date")
    private LocalDateTime repairDate;

    public RepairHistory() {
    }

    public RepairHistory(Integer id, Integer userId, Integer bikeId, LocalDateTime breakdownDate) {
        this.id = id;
        this.userId = userId;
        this.bikeId = bikeId;
        this.breakdownDate = breakdownDate;
    }

    public RepairHistory(Integer id, Integer userId, Integer bikeId, LocalDateTime breakdownDate, LocalDateTime repairDate) {
        this.id = id;
        this.userId = userId;
        this.bikeId = bikeId;
        this.breakdownDate = breakdownDate;
        this.repairDate = repairDate;
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

    public Integer getBikeId() {
        return bikeId;
    }

    public void setBikeId(Integer bikeId) {
        this.bikeId = bikeId;
    }

    public LocalDateTime getBreakdownDate() {
        return breakdownDate;
    }

    public void setBreakdownDate(LocalDateTime breakdownDate) {
        this.breakdownDate = breakdownDate;
    }

    public LocalDateTime getRepairDate() {
        return repairDate;
    }

    public void setRepairDate(LocalDateTime repairDate) {
        this.repairDate = repairDate;
    }
}
