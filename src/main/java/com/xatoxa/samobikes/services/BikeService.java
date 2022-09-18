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
        Part part;
        if (bike.getPart() == null) {
            part = new Part();
            bike.setPart(part);
            part.setBike(bike);
        }
        else {
            part = bike.getPart();
        }
        part.setId_bike(bike.getId());
        bikeRepository.save(bike);
    }

    public void deleteById(Integer id){
        bikeRepository.deleteById(id);
    }
}
