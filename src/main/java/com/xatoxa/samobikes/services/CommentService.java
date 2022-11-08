package com.xatoxa.samobikes.services;

import com.xatoxa.samobikes.entities.Comment;
import com.xatoxa.samobikes.repositories.CommentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Collection;

@Service
public class CommentService {

    CommentRepository commentRepository;

    @Autowired
    public void setCommentRepository(CommentRepository commentRepository) {
        this.commentRepository = commentRepository;
    }

    public Collection<Comment> findAll(){
        return commentRepository.findAll();
    }

    public Collection<Comment> findByBikeId(Integer bikeId, String sortField, String sortDir){
        if (sortDir.equals("asc")) return commentRepository.findByBikeIdOrderByCommentedAtAsc(bikeId);
        else return commentRepository.findByBikeIdOrderByCommentedAtDesc(bikeId);
    }

    public void save(Comment comment){
        commentRepository.save(comment);
    }

    public void insert(Integer userId,Integer bikeId,String commentText,LocalDateTime commentedAt){
        commentRepository.insert(userId, bikeId, commentText, commentedAt);
    }

    public void deleteById(Integer id){
        commentRepository.deleteById(id);
    }

    public Comment getById(Integer id){
        return commentRepository.findById(id).get();
    }
}

