package com.xatoxa.samobikes.controllers;

import com.xatoxa.samobikes.entities.Bike;
import com.xatoxa.samobikes.entities.Part;
import com.xatoxa.samobikes.services.PartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/parts")
public class PartController {
    private PartService partService;

    @Autowired
    public void setPartService (PartService partService){
        this.partService = partService;
    }

    @GetMapping("/fine/{id_bike}")
    public String setAllTrue(Model model, @PathVariable(value = "id_bike")Integer id) {
        Part part = partService.getById(id);
        part.setAllTrue();
        Bike bike = part.getBike();
        bike.setStatus(true);
        partService.add(part);
        model.addAttribute("bike", bike);
        model.addAttribute("part", part);
        return "bike-page";
    }
}
