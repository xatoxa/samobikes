package com.xatoxa.samobikes.controllers;

import com.xatoxa.samobikes.entities.Bike;
import com.xatoxa.samobikes.entities.Part;
import com.xatoxa.samobikes.entities.User;
import com.xatoxa.samobikes.services.BikeService;
import com.xatoxa.samobikes.services.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

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
        return showBikesByPage(model, 1, "number", "asc", null);
    }

    @GetMapping("/page/{pageNum}")
    public String showBikesByPage(Model model,
                                  @PathVariable(name = "pageNum") int pageNum,
                                  @Param("sortField") String sortField,
                                  @Param("sortDir") String sortDir,
                                  @Param("keyword") String keyword){
        Bike bike = new Bike();
        model.addAttribute("bike", bike);

        Page<Bike> page = bikeService.getAllByPage(pageNum, sortField, sortDir, keyword);
        List<Bike> bikes = page.getContent();

        model.addAttribute("bikes", bikes);
        model.addAttribute("count_all_bikes", bikeService.countAll());
        model.addAttribute("count_working_bikes", bikeService.countWorkingBikes());
        model.addAttribute("count_broken_bikes", bikeService.countBrokenBikes());

        long startCount = (long) (pageNum - 1) * BikeService.BIKES_PER_PAGE + 1;
        long endCount = startCount + BikeService.BIKES_PER_PAGE - 1;
        if (endCount > page.getTotalElements()){
            endCount = page.getTotalElements();
        }
        model.addAttribute("totalPages", page.getTotalPages());
        model.addAttribute("currentPage", pageNum);
        model.addAttribute("startCount", startCount);
        model.addAttribute("endCount", endCount);
        model.addAttribute("totalItems", page.getTotalElements());

        String reverseSortDir = sortDir.equals("asc") ? "desc" : "asc";
        model.addAttribute("sortField", sortField);
        model.addAttribute("sortDir", sortDir);
        model.addAttribute("reverseSortDir", reverseSortDir);

        model.addAttribute("keyword", keyword);

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
