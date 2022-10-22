package com.xatoxa.samobikes.repositories;

import com.xatoxa.samobikes.entities.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface UserRepository extends PagingAndSortingRepository<User, Integer> {
    public User findOneByUsername(String username);

    @Transactional
    @Modifying
    @Query("UPDATE User u SET u.enabled = ?2 WHERE u.id = ?1")
    public void setEnabledById(Integer id, boolean enabled);

    @Query("SELECT u FROM User u WHERE CONCAT(u.id, ' ', u.username, ' ', u.firstName, ' ', u.lastName) LIKE %?1%")
    public Page<User> findAll(String keyword, Pageable pageable);
}
