package com.xatoxa.samobikes.controllers;

import com.xatoxa.samobikes.entities.*;
import com.xatoxa.samobikes.services.BikeService;
import com.xatoxa.samobikes.services.CommentService;
import com.xatoxa.samobikes.services.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDateTime;

@Controller
public class CommentController {
    CommentService commentService;

    BikeService bikeService;

    UserServiceImpl userService;

    @Autowired
    public void setCommentService(CommentService commentService) {
        this.commentService = commentService;
    }

    @Autowired
    public void setBikeService(BikeService bikeService) {
        this.bikeService = bikeService;
    }

    @Autowired
    public void setUserService(UserServiceImpl userService) {
        this.userService = userService;
    }

    @PostMapping("/comment/save/{id}")
    public String saveComment (Model model, @PathVariable(value = "id") Integer id,
                               @AuthenticationPrincipal SamUserDetails loggedUser,
                               @RequestParam(value = "sortDir") String sortDir,
                               @RequestParam(value = "text") String text){
        Comment comment = new Comment();
        comment.setCommentText(text);
        comment.setCommentedAt(LocalDateTime.now());

        Bike bike = bikeService.getById(id);
        bike.addComment(comment);

        User user = userService.findByUserName(loggedUser.getUsername());
        user.addComment(comment);

        comment.setBike(bike);
        comment.setUser(user);

        commentService.insert(user.getId(), bike.getId(), comment.getCommentText(), comment.getCommentedAt());
        //userService.save(user);
        //bikeService.save(bike);

        Comment newComment = new Comment();
        Part part = bike.getPart();

        String reverseSortDir = sortDir.equals("asc") ? "desc" : "asc";

        model.addAttribute("comment", newComment);
        model.addAttribute("comments", commentService.findByBikeId(id, "commentedAt", sortDir));
        model.addAttribute("bike", bike);
        model.addAttribute("part", part);
        model.addAttribute("sortField", "commentedAt");
        model.addAttribute("sortDir", sortDir);
        model.addAttribute("reverseSortDir", reverseSortDir);

        return "redirect:/bikes/show/" + id + "?sortField=commentedAt&sortDir=" + sortDir;
    }
}
