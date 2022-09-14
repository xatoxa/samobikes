package com.xatoxa.samobikes.services;

import com.xatoxa.samobikes.entities.Bike;
import com.xatoxa.samobikes.entities.Part;
import com.xatoxa.samobikes.repositories.BikeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BikeService {
    private BikeRepository bikeRepository;

    @Autowired
    public void setBikeRepository(BikeRepository bikeRepository){
        this.bikeRepository = bikeRepository;
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

    public void add(Bike bike){
        Part part = new Part();
        bike.setPart(part);
        bikeRepository.save(bike);
    }

    public void deleteById(Integer id){
        bikeRepository.deleteById(id);
    }
}
