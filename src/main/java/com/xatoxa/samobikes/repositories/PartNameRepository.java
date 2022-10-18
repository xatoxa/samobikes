package com.xatoxa.samobikes.repositories;

import com.xatoxa.samobikes.entities.PartName;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PartNameRepository extends JpaRepository<PartName, Integer> {
}
