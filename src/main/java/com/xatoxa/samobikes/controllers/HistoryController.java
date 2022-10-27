package com.xatoxa.samobikes.controllers;

import com.xatoxa.samobikes.entities.Bike;
import com.xatoxa.samobikes.entities.History;
import com.xatoxa.samobikes.services.BikeService;
import com.xatoxa.samobikes.services.HistoryService;
import com.xatoxa.samobikes.services.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Controller
public class HistoryController {
    private HistoryService historyService;

    private UserServiceImpl userService;

    private BikeService bikeService;

    @Autowired
    public void setHistoryService(HistoryService historyService) {
        this.historyService = historyService;
    }

    @Autowired
    public void setUserService(UserServiceImpl userService) {
        this.userService = userService;
    }

    @Autowired
    public void setBikeService(BikeService bikeService) {
        this.bikeService = bikeService;
    }

    @GetMapping("/history")
    public String showHistory(Model model){
        return showHistoryByPage(model, 1);
    }

    @GetMapping("/history/page/{pageNum}")
    public String showHistoryByPage(Model model,
                                  @PathVariable(name = "pageNum") int pageNum){
        Page<History> page = historyService.getAllByPage(pageNum);
        List<History> historyList = page.getContent();
        historyList.forEach(s -> {
            s.setUsername(userService.getById(s.getUserId()).getUsername());
            Bike bike = bikeService.getById(s.getBikeId());
            s.setNumber(bike.getNumber());
            s.setQrNumber(bike.getQrNumber());
            s.setVIN(bike.getVIN());
        });

        model.addAttribute("history", historyList);

        long startCount = (long) (pageNum - 1) * UserServiceImpl.USERS_PER_PAGE + 1;
        long endCount = startCount + UserServiceImpl.USERS_PER_PAGE - 1;
        if (endCount > page.getTotalElements()){
            endCount = page.getTotalElements();
        }
        model.addAttribute("totalPages", page.getTotalPages());
        model.addAttribute("currentPage", pageNum);
        model.addAttribute("startCount", startCount);
        model.addAttribute("endCount", endCount);
        model.addAttribute("totalItems", page.getTotalElements());

        return "repair-history";
    }

    @GetMapping("/history/clean")
    public String cleanHistory(){
        historyService.clean();

        return "redirect:/history";
    }

}
