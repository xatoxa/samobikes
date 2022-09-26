package com.xatoxa.samobikes.repositories;

import com.xatoxa.samobikes.entities.Bike;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BikeRepository extends PagingAndSortingRepository<Bike, Integer> {
    int countByStatus (boolean status);
    Bike findByNumber(Integer number);
    Bike findByqrNumber(Integer qrNumber);
    Bike findByVIN(String VIN);

}
