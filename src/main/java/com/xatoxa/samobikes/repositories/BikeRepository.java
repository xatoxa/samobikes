package com.xatoxa.samobikes.repositories;

import com.xatoxa.samobikes.entities.Bike;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BikeRepository extends JpaRepository<Bike, Integer> {
    List<Bike> findByStatus (boolean status);
    Bike findByNumber(Integer number);
    Bike findByqrNumber(Integer qrNumber);
    Bike findByVIN(String VIN);

}
