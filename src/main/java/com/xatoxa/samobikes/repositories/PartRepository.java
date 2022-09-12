package com.xatoxa.samobikes.repositories;

import com.xatoxa.samobikes.entities.Part;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PartRepository extends JpaRepository<Part, Integer> {
}
