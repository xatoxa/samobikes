package com.xatoxa.samobikes.repositories;

import com.xatoxa.samobikes.entities.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Collection;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Integer> {
    Collection<Comment> findByBikeIdOrderByCommentedAtDesc(Integer bikeId);

    Collection<Comment> findByBikeIdOrderByCommentedAtAsc(Integer bikeId);

    @Transactional
    @Modifying
    @Query(value = "INSERT INTO comments (user_id, bike_id, comment_text, commented_at) VALUES (:userId, :bikeId, :commentText, :commentedAt)",
            nativeQuery = true)
    void insert(@Param("userId") Integer userId, @Param("bikeId") Integer bikeId,
                @Param("commentText") String commentText, @Param("commentedAt") LocalDateTime commentedAt);
}
