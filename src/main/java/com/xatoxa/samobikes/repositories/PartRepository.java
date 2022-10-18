package com.xatoxa.samobikes.repositories;

import com.xatoxa.samobikes.entities.Part;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface PartRepository extends JpaRepository<Part, Integer> {
    @Modifying
    @Transactional
    @Query("DELETE FROM Part u WHERE u.name = :name")
    void deleteByName(@Param("name") String name);
}
