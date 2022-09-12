package com.xatoxa.samobikes.controllers;

import com.xatoxa.samobikes.entities.Bike;
import com.xatoxa.samobikes.services.BikeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/bikes")
public class BikesController {
    private BikeService bikeService;

    @Autowired
    public void setBikeService(BikeService bikeService) {
        this.bikeService = bikeService;
    }

    @GetMapping
    public String showBikes(Model model){
        Bike bike = new Bike();
        model.addAttribute("bikes" ,bikeService.getAllBikes());
        model.addAttribute("bike", bike);
        return "bikes";
    }

    @GetMapping("/show/{id}")
    public String showOneBike(Model model, @PathVariable(value = "id") Integer id){
        Bike bike = bikeService.getById(id);
        model.addAttribute("bike", bike);
        return "bike-page";
    }

    @GetMapping("/add")
    public String showAddBikeForm(Model model){
        Bike bike = new Bike();
        model.addAttribute("bike", bike);
        return "bike-edit";
    }

    @GetMapping("/edit/{id}")
    public String showEditBikeForm(Model model, @PathVariable(value = "id") Integer id){
        Bike bike = bikeService.getById(id);
        model.addAttribute("bike", bike);
        return "bike-edit";
    }

    @PostMapping("/edit")
    public String addBike (@ModelAttribute(value = "bike") Bike bike){
        bikeService.add(bike);
        return "redirect:/bikes";
    }

    @GetMapping("/delete/{id}")
    public String deleteBike(@PathVariable(value = "id") Integer id){
        bikeService.deleteById(id);
        return "redirect:/bikes";
    }
}
