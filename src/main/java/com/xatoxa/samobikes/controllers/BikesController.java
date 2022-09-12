package com.xatoxa.samobikes.controllers;

import com.xatoxa.samobikes.entities.Bike;
import com.xatoxa.samobikes.services.BikeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

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

    @PostMapping("/add")
    public String addBike (@ModelAttribute(value = "bike") Bike bike){
        bikeService.add(bike);
        return "redirect:/bikes";
    }
}
