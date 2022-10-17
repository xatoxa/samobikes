package com.xatoxa.samobikes.controllers;

import com.xatoxa.samobikes.entities.Bike;
import com.xatoxa.samobikes.entities.Comment;
import com.xatoxa.samobikes.entities.Part;
import com.xatoxa.samobikes.services.BikeService;
import com.xatoxa.samobikes.services.PartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/parts")
public class PartController {
    private PartService partService;
    private BikeService bikeService;

    @Autowired
    public void setPartService (PartService partService){
        this.partService = partService;
    }

    @Autowired
    public void setBikeService (BikeService bikeService){
        this.bikeService = bikeService;
    }

    @GetMapping("/edit/{id_bike}")
    public String showEditPartForm(Model model, @PathVariable(value = "id_bike") Integer id,
                                   @Param("currentPage") String currentPage,
                                   @Param("sortField") String sortField,
                                   @Param("sortDir") String sortDir,
                                   @Param("commentSortField") String commentSortField,
                                   @Param("commentSortDir") String commentSortDir,
                                   @Param("keyword") String keyword){
        Part part = partService.getById(id);
        Bike bike = part.getBike();
        model.addAttribute("part", part);
        model.addAttribute("bike", bike);

        String reverseSortDir = sortDir.equals("asc") ? "desc" : "asc";
        String commentReverseSortDir = commentSortDir.equals("asc") ? "desc" : "asc";

        model.addAttribute("sortField", sortField);
        model.addAttribute("sortDir", sortDir);
        model.addAttribute("reverseSortDir", reverseSortDir);
        model.addAttribute("commentSortField", commentSortField);
        model.addAttribute("commentSortDir", commentSortDir);
        model.addAttribute("commentReverseSortDir", commentReverseSortDir);
        model.addAttribute("currentPage", currentPage);
        model.addAttribute("keyword", keyword);

        return "part-edit";
    }

    @PostMapping("/edit")
    public String editPart (Model model, @ModelAttribute(value = "part") Part part){
        partService.save(part);
        Bike bike = part.getBike();
        bike.checkWorks();
        bikeService.save(bike);
        model.addAttribute("part", part);
        model.addAttribute("bike", bike);
        model.addAttribute("comment", new Comment());
        model.addAttribute("comments", bike.getComments());
        return "redirect:/bikes";
    }

    @GetMapping("/fine/{id_bike}")
    public String setAllTrue(Model model, @PathVariable(value = "id_bike")Integer id,
                             @Param("currentPage") String currentPage,
                             @Param("sortField") String sortField,
                             @Param("sortDir") String sortDir,
                             @Param("commentSortField") String commentSortField,
                             @Param("commentSortDir") String commentSortDir,
                             @Param("keyword") String keyword) {
        Part part = partService.getById(id);
        part.setAllTrue();
        Bike bike = part.getBike();
        bike.setStatus(true);
        bikeService.save(bike);
        partService.save(part);
        model.addAttribute("bike", bike);
        model.addAttribute("part", part);
        model.addAttribute("comment", new Comment());
        model.addAttribute("comments", bike.getComments());

        String reverseSortDir = sortDir.equals("asc") ? "desc" : "asc";
        String commentReverseSortDir = commentSortDir.equals("asc") ? "desc" : "asc";

        model.addAttribute("sortField", sortField);
        model.addAttribute("sortDir", sortDir);
        model.addAttribute("reverseSortDir", reverseSortDir);
        model.addAttribute("commentSortField", commentSortField);
        model.addAttribute("commentSortDir", commentSortDir);
        model.addAttribute("commentReverseSortDir", commentReverseSortDir);
        model.addAttribute("currentPage", currentPage);
        model.addAttribute("keyword", keyword);
        return "redirect:/bikes/show/" + id + "?currentPage=" + currentPage + "&sortField=" + sortField + "&sortDir=" + sortDir + "&commentSortField=commentedAt&commentSortDir=" + commentSortDir + (keyword != null ? "&keyword=" + keyword : "");
    }
}
