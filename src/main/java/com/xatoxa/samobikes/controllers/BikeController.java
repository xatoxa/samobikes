package com.xatoxa.samobikes.controllers;

import com.xatoxa.samobikes.FileUploadUtil;
import com.xatoxa.samobikes.entities.Bike;
import com.xatoxa.samobikes.entities.Comment;
import com.xatoxa.samobikes.entities.Part;
import com.xatoxa.samobikes.entities.User;
import com.xatoxa.samobikes.services.BikeService;
import com.xatoxa.samobikes.services.CommentService;
import com.xatoxa.samobikes.services.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.integration.IntegrationProperties;
import org.springframework.data.domain.Page;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/bikes")
public class BikeController {
    private BikeService bikeService;
    private CommentService commentService;

    @Autowired
    public void setBikeService(BikeService bikeService) {
        this.bikeService = bikeService;
    }

    @Autowired
    public void setCommentService(CommentService commentService){
        this.commentService = commentService;
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
    public String showOneBike(Model model, @PathVariable(value = "id") Integer id,
                              @Param("currentPage") String currentPage,
                              @Param("sortField") String sortField,
                              @Param("sortDir") String sortDir,
                              @Param("commentSortField") String commentSortField,
                              @Param("commentSortDir") String commentSortDir,
                              @Param("keyword") String keyword){
        Comment comment = new Comment();

        Bike bike = bikeService.getById(id);
        bike.checkWorks();
        bikeService.save(bike);

        List<Part> parts = bike.getParts().stream().sorted(Comparator.comparingInt(Part::getImportance)).toList();
        List<Part> brokenParts = parts.stream().filter(s -> !s.isStatus()).toList();
        List<Part> workingParts = parts.stream().filter(Part::isStatus).toList();

        int wTemp = 0, bTemp = 0;

        if (brokenParts.size() % 2 != 0) bTemp = 1;
        if (workingParts.size() % 2 != 0) wTemp = 1;

        List<Part> brokenPartsLeft = brokenParts.subList(0, brokenParts.size() / 2 + bTemp);
        List<Part> brokenPartsRight = brokenParts.subList(brokenParts.size() / 2 + bTemp, brokenParts.size());
        List<Part> workingPartsLeft = workingParts.subList(0, workingParts.size() / 2 + wTemp);
        List<Part> workingPartsRight = workingParts.subList(workingParts.size() / 2 + wTemp, workingParts.size());

        model.addAttribute("comment", comment);
        model.addAttribute("comments", commentService.findByBikeId(id, commentSortField, commentSortDir));
        model.addAttribute("bike", bike);
        model.addAttribute("brokenPartsLeft", brokenPartsLeft);
        model.addAttribute("brokenPartsRight", brokenPartsRight);
        model.addAttribute("workingPartsLeft", workingPartsLeft);
        model.addAttribute("workingPartsRight", workingPartsRight);

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
    public String saveBike (Model model, @ModelAttribute(value = "bike") Bike bike,
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

        redirectAttributes.addFlashAttribute(
                "message",
                "Велосипед " + bike.getNumber() + " | " + bike.getQrNumber() + " сохранён.");

        return "redirect:/bikes";
    }

    private List<String> checkDuplicate(Bike bike, List<String> errors) {
        if (!bikeService.isNumberUnique(bike.getId(), bike.getNumber())) errors.add("Такой номер уже существует");
        if (!bikeService.isQRNumberUnique(bike.getId(), bike.getQrNumber())) errors.add("Такой QR номер уже существует");
        if (!bikeService.isVINUnique(bike.getId(), bike.getVIN())) errors.add("Такой VIN уже существует");

        return errors;
    }

    @GetMapping("/management/delete/{id}")
    public String deleteBike(@PathVariable(value = "id") Integer id,
                             RedirectAttributes redirectAttributes){
        Integer number = bikeService.getById(id).getNumber();
        Integer qrNumber = bikeService.getById(id).getQrNumber();
        bikeService.deleteById(id);

        redirectAttributes.addFlashAttribute(
                "message",
                "Велосипед " + number + " | " + qrNumber + " удалён.");
        return "redirect:/bikes";
    }
}
