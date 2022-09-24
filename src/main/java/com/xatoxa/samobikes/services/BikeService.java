package com.xatoxa.samobikes.services;

import com.xatoxa.samobikes.entities.Bike;
import com.xatoxa.samobikes.entities.Part;
import com.xatoxa.samobikes.repositories.BikeRepository;
import com.xatoxa.samobikes.repositories.PartRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BikeService {
    private BikeRepository bikeRepository;
    private PartRepository partRepository;

    @Autowired
    public void setBikeRepository(BikeRepository bikeRepository){
        this.bikeRepository = bikeRepository;
    }

    @Autowired
    public void setPartRepository(PartRepository partRepository){
        this.partRepository = partRepository;
    }

    public List<Bike> getAllBikes(){
        return bikeRepository.findAll();
    }

    public List<Bike> getBrokenBikes(){
        return bikeRepository.findByStatus(false);
    }

    public Bike getById(Integer id){
        return bikeRepository.getReferenceById(id);
    }

    public void save(Bike bike){
        if (bike.getPart() == null) {
            Part part = new Part();
            bike.setPart(part);
            part.setBike(bike);
        }
        bikeRepository.save(bike);
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
        //если создаётся
        if(id == null){
            if (bike != null) {
                return false;
            }
        }else {
            if (bike.getId() != id){
                return false;
            }
        }

        return true;
    }
}
