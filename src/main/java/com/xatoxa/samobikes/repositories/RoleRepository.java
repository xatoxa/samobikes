package com.xatoxa.samobikes.repositories;

import com.xatoxa.samobikes.entities.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<Role, Integer> {
    public Role findOneByName(String name);
}
