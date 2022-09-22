package com.xatoxa.samobikes.controllers;

import com.xatoxa.samobikes.entities.Bike;
import com.xatoxa.samobikes.entities.Part;
import com.xatoxa.samobikes.services.BikeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/bikes")
public class BikeController {
    private BikeService bikeService;

    @Autowired
    public void setBikeService(BikeService bikeService) {
        this.bikeService = bikeService;
    }

    //добавить варианты сортировки
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
        bike.checkWorks();
        bikeService.save(bike);
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
    public String saveBike (@ModelAttribute(value = "bike") Bike bike,
                            RedirectAttributes redirectAttributes){
        //добавить проверку на совпадающий id, если да, редирект обратно
        //добавить проверку на заполненные значения id, number, VIN
        bikeService.save(bike);
        redirectAttributes.addFlashAttribute("message", "Успешно выполнено.");
        return "redirect:/bikes";
    }

    @GetMapping("/delete/{id}")
    public String deleteBike(@PathVariable(value = "id") Integer id){
        bikeService.deleteById(id);
        return "redirect:/bikes";
    }
}
