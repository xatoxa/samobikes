package com.xatoxa.samobikes.services;

import com.xatoxa.samobikes.entities.Comment;
import com.xatoxa.samobikes.repositories.CommentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

    public Collection<Comment> findByBikeId(Integer bikeId){
        return commentRepository.findByBikeId(bikeId);
    }

    public void save(Comment comment){
        commentRepository.save(comment);
    }
}

