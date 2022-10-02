package com.xatoxa.samobikes.repositories;

import com.xatoxa.samobikes.entities.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Integer> {
    Collection<Comment> findByBikeId(Integer bikeId);
}
