package com.xatoxa.samobikes.services;

import com.xatoxa.samobikes.entities.Bike;
import com.xatoxa.samobikes.entities.Part;
import com.xatoxa.samobikes.entities.PartName;
import com.xatoxa.samobikes.entities.User;
import com.xatoxa.samobikes.repositories.BikeRepository;
import com.xatoxa.samobikes.repositories.PartRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
public class BikeService {
    public static final int BIKES_PER_PAGE = 10;
    private BikeRepository bikeRepository;
    private PartService partService;


    @Autowired
    public void setBikeRepository(BikeRepository bikeRepository){
        this.bikeRepository = bikeRepository;
    }

    @Autowired
    public void setPartService(PartService partService){
        this.partService = partService;
    }

    public Iterable<Bike> getAllBikes(){
        return bikeRepository.findAll();
    }

    public Page<Bike> getAllByPage(int pageNum, String sortField, String sortDir, String keyword){
        Sort sort = Sort.by(sortField, "number");
        if ("asc".equals(sortDir)){
            sort = sort.ascending();
        } else{
            sort = sort.descending();
        }
        Pageable pageable = PageRequest.of(pageNum - 1, BIKES_PER_PAGE, sort);

        if (keyword != null){
            return bikeRepository.findAll(keyword, pageable);
        }
        return bikeRepository.findAll(pageable);
    }

    public int countAll(){
        return (int)bikeRepository.count();
    }

    public int countWorkingBikes(){
        return bikeRepository.countByStatus(true);
    }


    public int countBrokenBikes(){
        return bikeRepository.countByStatus(false);
    }

    public Bike getById(Integer id){
        return bikeRepository.findById(id).get();
    }

    public void save(Bike bike){
        bikeRepository.save(bike);
        List<Part> parts = bike.getParts();
        if (parts.size() == 0){
            parts = partService.createParts(bike);
            bike.setParts(parts);
        }
    }

    public void deleteById(Integer id){
        bikeRepository.deleteById(id);
    }

    public boolean isNumberUnique(Integer id, Integer number) {
        Bike bike = bikeRepository.findByNumber(number);
        if (bike == null) return true;

        return checkUniqueness(id, bike);
    }

    public boolean isQRNumberUnique(Integer id, Integer qrNumber) {
        Bike bike = bikeRepository.findByqrNumber(qrNumber);
        if (bike == null) return true;

        return checkUniqueness(id, bike);
    }

    public boolean isVINUnique(Integer id, String VIN) {
        Bike bike = bikeRepository.findByVIN(VIN);
        if (bike == null) return true;


        return checkUniqueness(id, bike);
    }

    private boolean checkUniqueness(Integer id, Bike bike){
        if(id == null){
            return bike == null;
        }else {
            return Objects.equals(bike.getId(), id);
        }
    }
}
