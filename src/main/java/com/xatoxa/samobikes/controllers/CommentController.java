package com.xatoxa.samobikes.controllers;

import com.xatoxa.samobikes.entities.*;
import com.xatoxa.samobikes.services.BikeService;
import com.xatoxa.samobikes.services.CommentService;
import com.xatoxa.samobikes.services.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDateTime;
import java.util.List;

import static com.xatoxa.samobikes.Utils.StringUtil.reverseSortDir;

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
                               @RequestParam(value = "currentPage") String currentPage,
                               @RequestParam(value = "sortField") String sortField,
                               @RequestParam(value = "sortDir") String sortDir,
                               @RequestParam(value = "commentSortField") String commentSortField,
                               @RequestParam(value = "commentSortDir") String commentSortDir,
                               @RequestParam(value = "keyword") String keyword,
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

        Comment newComment = new Comment();
        List<Part> parts = bike.getParts();

        model.addAttribute("comment", newComment);
        model.addAttribute("comments", commentService.findByBikeId(id, "commentedAt", sortDir));
        model.addAttribute("bike", bike);
        model.addAttribute("parts", parts);
        model.addAttribute("sortField", sortField);
        model.addAttribute("sortDir", sortDir);
        model.addAttribute("reverseSortDir", reverseSortDir(sortDir));
        model.addAttribute("commentSortField", commentSortField);
        model.addAttribute("commentSortDir", commentSortDir);
        model.addAttribute("commentReverseSortDir", reverseSortDir(commentSortDir));
        model.addAttribute("currentPage", currentPage);
        model.addAttribute("keyword", keyword);

        return "redirect:/bikes/show/" + id + "?currentPage=" + currentPage + "&sortField=" + sortField + "&sortDir=" + sortDir + "&commentSortField=commentedAt&commentSortDir=" + commentSortDir + (keyword != null ? "&keyword=" + keyword : "");
    }

    @GetMapping("/comment/delete/{id}")
    public String deleteComment(Model model, @PathVariable(value = "id") Integer id,
                             @RequestParam(value = "currentPage") String currentPage,
                             @RequestParam(value = "sortField") String sortField,
                             @RequestParam(value = "sortDir") String sortDir,
                             @RequestParam(value = "commentSortField") String commentSortField,
                             @RequestParam(value = "commentSortDir") String commentSortDir,
                             @RequestParam(value = "keyword") String keyword){
        Comment comment = commentService.getById(id);
        Bike bike = comment.getBike();

        commentService.deleteById(id);

        Comment newComment = new Comment();
        List<Part> parts = bike.getParts();

        model.addAttribute("comment", newComment);
        model.addAttribute("comments", commentService.findByBikeId(bike.getId(), "commentedAt", sortDir));
        model.addAttribute("bike", bike);
        model.addAttribute("parts", parts);
        model.addAttribute("sortField", sortField);
        model.addAttribute("sortDir", sortDir);
        model.addAttribute("reverseSortDir", reverseSortDir(sortDir));
        model.addAttribute("commentSortField", commentSortField);
        model.addAttribute("commentSortDir", commentSortDir);
        model.addAttribute("commentReverseSortDir", reverseSortDir(commentSortDir));
        model.addAttribute("currentPage", currentPage);
        model.addAttribute("keyword", keyword);

        return "redirect:/bikes/show/" + bike.getId() + "?currentPage=" + currentPage + "&sortField=" + sortField + "&sortDir=" + sortDir + "&commentSortField=commentedAt&commentSortDir=" + commentSortDir + (keyword != null ? "&keyword=" + keyword : "");
    }
}
