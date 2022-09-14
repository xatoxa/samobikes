package com.xatoxa.samobikes.controllers;

import com.xatoxa.samobikes.entities.Bike;
import com.xatoxa.samobikes.entities.Part;
import com.xatoxa.samobikes.services.BikeService;
import com.xatoxa.samobikes.services.PartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/bikes")
public class BikeController {
    private BikeService bikeService;

    @Autowired
    public void setBikeService(BikeService bikeService) {
        this.bikeService = bikeService;
    }

    @GetMapping
    public String showBikes(Model model){
        Bike bike = new Bike();
        model.addAttribute("bikes" ,bikeService.getAllBikes());
        model.addAttribute("broken_bikes", bikeService.getBrokenBikes());
        model.addAttribute("bike", bike);
        return "bikes";
    }

    @GetMapping("/show/{id}")
    public String showOneBike(Model model, @PathVariable(value = "id") Integer id){
        Bike bike = bikeService.getById(id);
        Part part = bike.getPart();
        model.addAttribute("bike", bike);
        model.addAttribute("part", part);
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
        //добавить проверку на совпадающий id, если да, редирект обратно
        bikeService.add(bike);
        return "redirect:/bikes";
    }

    @GetMapping("/delete/{id}")
    public String deleteBike(@PathVariable(value = "id") Integer id){
        bikeService.deleteById(id);
        return "redirect:/bikes";
    }
}
