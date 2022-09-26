package com.xatoxa.samobikes.repositories;

import com.xatoxa.samobikes.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends PagingAndSortingRepository<User, Integer> {
    public User findOneByUsername(String username);
}
