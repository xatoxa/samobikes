package com.xatoxa.samobikes.controllers;

import com.xatoxa.samobikes.entities.Bike;
import com.xatoxa.samobikes.entities.PartListDTO;
import com.xatoxa.samobikes.entities.PartName;
import com.xatoxa.samobikes.services.PartNameService;
import com.xatoxa.samobikes.services.PartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
public class PartNameController {
    PartNameService partNameService;

    PartService partService;

    @Autowired
    public void setPartNameService(PartNameService partNameService) {
        this.partNameService = partNameService;
    }

    @Autowired
    public void setPartService(PartService partService) {
        this.partService = partService;
    }

    @GetMapping("/part-names")
    public String showPartNamesTable(Model model){
        List<PartName> partNames = partNameService.getAll();
        model.addAttribute("partNames", partNames);

        return "part-names";
    }

    @GetMapping("/part-names/add")
    public String showAddBikeForm(Model model){
        PartName partName = new PartName();
        model.addAttribute("partName", partName);
        return "part-names-edit";
    }

    /*@GetMapping("/part-names/edit/{id}")
    public String showEditBikeForm(Model model, @PathVariable(value = "id") Integer id){
        PartName partName = partNameService.getById(id);
        model.addAttribute("partName", partName);
        return "part-names-edit";
    }*/

    @PostMapping("/part-names/edit")
    public String saveBike (@ModelAttribute(value = "partName") PartName partName,
                            RedirectAttributes redirectAttributes){
        partService.saveForAll(partName);
        partNameService.save(partName);
        redirectAttributes.addFlashAttribute(
                "message",
                "Запчасть " + partName.getName()  + " сохранена для всех велосипедов.");
        return "redirect:/part-names";
    }

    @GetMapping("/part-names/delete/{id}")
    public String deletePartName(@PathVariable(value = "id") Integer id,
                             RedirectAttributes redirectAttributes){
        PartName partName = partNameService.getById(id);
        partNameService.deleteById(id);
        partService.deleteByName(partName.getName());
        redirectAttributes.addFlashAttribute(
                "message",
                "Запчасть " + partName.getName()  + " удалена из всех велосипедов.");
        return "redirect:/part-names";
    }
}
