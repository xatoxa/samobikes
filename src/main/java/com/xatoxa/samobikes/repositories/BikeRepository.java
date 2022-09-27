package com.xatoxa.samobikes.repositories;

import com.xatoxa.samobikes.entities.Bike;
import com.xatoxa.samobikes.entities.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BikeRepository extends PagingAndSortingRepository<Bike, Integer> {
    int countByStatus (boolean status);
    Bike findByNumber(Integer number);
    Bike findByqrNumber(Integer qrNumber);
    Bike findByVIN(String VIN);

    @Query("SELECT u FROM Bike u WHERE CONCAT(u.number, ' ', u.qrNumber, ' ', u.VIN) LIKE %?1%")
    public Page<Bike> findAll(String keyword, Pageable pageable);

}
