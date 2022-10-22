package com.xatoxa.samobikes.controllers;

import com.xatoxa.samobikes.entities.Bike;
import com.xatoxa.samobikes.entities.Comment;
import com.xatoxa.samobikes.entities.Part;
import com.xatoxa.samobikes.DTO.PartListDTO;
import com.xatoxa.samobikes.services.BikeService;
import com.xatoxa.samobikes.services.PartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
        Bike bike = bikeService.getById(id);
        List<Part> parts = bike.getParts();
        PartListDTO partListDTO = new PartListDTO();
        partListDTO.setBikeId(bike.getId());
        partListDTO.setParams(id + "?currentPage=" + currentPage + "&sortField=" + sortField + "&sortDir=" + sortDir + "&commentSortField=commentedAt&commentSortDir=" + commentSortDir + (keyword != null ? "&keyword=" + keyword : ""));

        int temp = 0;

        if (parts.size() % 2 != 0) temp = 1;

        List<Part> partsLeft = parts.subList(0, parts.size() / 2 + temp);
        List<Part> partsRight = parts.subList(parts.size() / 2 + temp, parts.size());
        partListDTO.setPartsLeft(partsLeft);
        partListDTO.setPartsRight(partsRight);

        model.addAttribute("partList", partListDTO);
        model.addAttribute("parts", parts);
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
    public String editPart (Model model, @ModelAttribute(value = "partList") PartListDTO partListDTO){
        Bike bike = bikeService.getById(partListDTO.getBikeId());
        List<Part> parts = bike.getParts();
        parts.clear();
        parts.addAll(partListDTO.getParts());
        bike.checkWorks();
        bikeService.save(bike);
        model.addAttribute("parts", bike.getParts());
        model.addAttribute("bike", bike);
        model.addAttribute("comment", new Comment());
        model.addAttribute("comments", bike.getComments());
        return "redirect:/bikes/show/" + partListDTO.getParams();
    }

    @GetMapping("/fine/{id_bike}")
    public String setAllImpTrue(Model model, @PathVariable(value = "id_bike")Integer id,
                             @Param("currentPage") String currentPage,
                             @Param("sortField") String sortField,
                             @Param("sortDir") String sortDir,
                             @Param("commentSortField") String commentSortField,
                             @Param("commentSortDir") String commentSortDir,
                             @Param("keyword") String keyword) {

        Bike bike = bikeService.getById(id);
        List<Part> parts = bike.getParts();
        parts.forEach(s -> {
            if (s.getImportance() < 3) s.setStatus(true);
        });
        bike.setParts(parts);
        bike.setStatus(true);
        bikeService.save(bike);
        model.addAttribute("bike", bike);
        model.addAttribute("parts", parts);
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

    @GetMapping("/fineAll/{id_bike}")
    public String setAllTrue(Model model, @PathVariable(value = "id_bike")Integer id,
                             @Param("currentPage") String currentPage,
                             @Param("sortField") String sortField,
                             @Param("sortDir") String sortDir,
                             @Param("commentSortField") String commentSortField,
                             @Param("commentSortDir") String commentSortDir,
                             @Param("keyword") String keyword) {

        Bike bike = bikeService.getById(id);
        List<Part> parts = bike.getParts();
        parts.forEach(s -> s.setStatus(true));
        bike.setParts(parts);
        bike.setStatus(true);
        bikeService.save(bike);
        model.addAttribute("bike", bike);
        model.addAttribute("parts", parts);
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
