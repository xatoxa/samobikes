package com.xatoxa.samobikes.controllers;

import com.xatoxa.samobikes.Utils.FileUploadUtil;
import com.xatoxa.samobikes.entities.*;
import com.xatoxa.samobikes.services.BikeService;
import com.xatoxa.samobikes.services.CommentService;
import com.xatoxa.samobikes.services.HistoryService;
import com.xatoxa.samobikes.services.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.repository.query.Param;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.*;

import static com.xatoxa.samobikes.Utils.StringUtil.reverseSortDir;

@Controller
@RequestMapping("/bikes")
public class BikeController {
    private BikeService bikeService;
    private CommentService commentService;

    private UserServiceImpl userService;

    private HistoryService historyService;

    @Autowired
    public void setBikeService(BikeService bikeService) {
        this.bikeService = bikeService;
    }

    @Autowired
    public void setCommentService(CommentService commentService){
        this.commentService = commentService;
    }

    @Autowired
    public void setUserService(UserServiceImpl userService) {
        this.userService = userService;
    }

    @Autowired
    public void setHistoryService(HistoryService historyService) {
        this.historyService = historyService;
    }

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
        model.addAttribute("sortField", sortField);
        model.addAttribute("sortDir", sortDir);
        model.addAttribute("reverseSortDir", reverseSortDir(sortDir));
        model.addAttribute("keyword", keyword);

        return "bikes";
    }

    @GetMapping("/show/{id}")
    public String showOneBike(Model model, @PathVariable(value = "id") Integer id,
                              @Param("currentPage") String currentPage,
                              @Param("sortField") String sortField,
                              @Param("sortDir") String sortDir,
                              @Param("commentSortField") String commentSortField,
                              @Param("commentSortDir") String commentSortDir,
                              @Param("keyword") String keyword){
        Bike bike = bikeService.getById(id);
        bike.checkWorks();
        bikeService.save(bike);

        List<Part> parts = bike.getParts().stream().sorted(Comparator.comparingInt(Part::getImportance)).toList();
        List<Part> brokenParts = parts.stream().filter(s -> !s.isStatus()).toList();
        List<Part> workingParts = parts.stream().filter(Part::isStatus).toList();

        model.addAttribute("comment", new Comment());
        model.addAttribute("comments", commentService.findByBikeId(id, commentSortField, commentSortDir));
        model.addAttribute("bike", bike);
        model.addAttribute("brokenPartsLeft", makeSubList(true, brokenParts));
        model.addAttribute("brokenPartsRight", makeSubList(false, brokenParts));
        model.addAttribute("workingPartsLeft", makeSubList(true, workingParts));
        model.addAttribute("workingPartsRight", makeSubList(false, workingParts));
        model.addAttribute("sortField", sortField);
        model.addAttribute("sortDir", sortDir);
        model.addAttribute("reverseSortDir", reverseSortDir(sortDir));
        model.addAttribute("commentSortField", commentSortField);
        model.addAttribute("commentSortDir", commentSortDir);
        model.addAttribute("commentReverseSortDir", reverseSortDir(commentSortDir));
        model.addAttribute("currentPage", currentPage);
        model.addAttribute("keyword", keyword);

        return "bike-page";
    }

    @GetMapping("/management/add")
    public String showAddBikeForm(Model model){
        Bike bike = new Bike();
        model.addAttribute("bike", bike);
        return "bike-edit";
    }

    @GetMapping("/management/edit/{id}")
    public String showEditBikeForm(Model model, @PathVariable(value = "id") Integer id){
        Bike bike = bikeService.getById(id);
        model.addAttribute("bike", bike);
        return "bike-edit";
    }

    @PostMapping("/management/edit")
    public String saveBike (Model model,
                            @AuthenticationPrincipal SamUserDetails loggedUser,
                            @ModelAttribute(value = "bike") Bike bike,
                            RedirectAttributes redirectAttributes,
                            @RequestParam("image")MultipartFile multipartFile) throws IOException {
        List<String> errors = new ArrayList<>();
        errors = checkDuplicate(bike, errors);
        if (!errors.isEmpty()){
            model.addAttribute("bike", bike);
            model.addAttribute("errors", errors);

            return "bike-edit";
        }

        if (!multipartFile.isEmpty()){
            String fileName = StringUtils.cleanPath(multipartFile.getOriginalFilename());

            bike.setPhoto(fileName);
            bikeService.save(bike);

            String uploadDir = "photos/bike-photos/" + bike.getId();
            FileUploadUtil.cleanDir(uploadDir);
            FileUploadUtil.saveFile(uploadDir, fileName, multipartFile);

        } else {
            if (bike.getPhoto().isEmpty()) bike.setPhoto("");
            bikeService.save(bike);
        }

        History history = new History(
                userService.findByUserName(loggedUser.getUsername()).getId(),
                bike.getId(),
                "Вело " + bike.getNumber() + " | " + bike.getQrNumber() + " | " + bike.getVIN() + " сохранён/изменён",
                LocalDateTime.now());
        historyService.save(history);

        redirectAttributes.addFlashAttribute(
                "message",
                "Велосипед " + bike.getNumber() + " | " + bike.getQrNumber() + " сохранён.");

        return "redirect:/bikes";
    }

    @GetMapping("/management/delete/{id}")
    public String deleteBike(@PathVariable(value = "id") Integer id,
                             @AuthenticationPrincipal SamUserDetails loggedUser,
                             RedirectAttributes redirectAttributes){
        Bike bike = bikeService.getById(id);
        Integer number = bike.getNumber();
        Integer qrNumber = bike.getQrNumber();
        String VIN = bike.getVIN();

        bikeService.deleteById(id);

        History history = new History(
                userService.findByUserName(loggedUser.getUsername()).getId(),
                id,
                "Вело " + number + " | " + qrNumber + " | " + VIN + " удалён",
                LocalDateTime.now());
        historyService.save(history);

        redirectAttributes.addFlashAttribute(
                "message",
                "Велосипед " + number + " | " + qrNumber + " удалён.");
        return "redirect:/bikes";
    }

    private List<String> checkDuplicate(Bike bike, List<String> errors) {
        if (!bikeService.isNumberUnique(bike.getId(), bike.getNumber()))
            errors.add("Такой номер уже существует");
        if (!bikeService.isQRNumberUnique(bike.getId(), bike.getQrNumber()))
            errors.add("Такой QR номер уже существует");
        if (!bikeService.isVINUnique(bike.getId(), bike.getVIN()))
            errors.add("Такой VIN уже существует");

        return errors;
    }

    private List<Part> makeSubList(boolean left, List<Part> parts){
        int temp = 0;

        if (parts.size() % 2 != 0) temp = 1;

        if (left)
            return parts.subList(0, parts.size() / 2 + temp);
        else
            return parts.subList(parts.size() / 2 + temp, parts.size());

    }
}
