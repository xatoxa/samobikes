package com.xatoxa.samobikes.services;

import com.xatoxa.samobikes.entities.Bike;
import com.xatoxa.samobikes.entities.Part;
import com.xatoxa.samobikes.entities.PartName;
import com.xatoxa.samobikes.repositories.PartRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class PartService {
    private PartRepository partRepository;

    private PartNameService partNameService;

    private BikeService bikeService;

    @Autowired
    public void setPartRepository(PartRepository partRepository){
        this.partRepository = partRepository;
    }

    @Autowired
    public void setPartNameService(PartNameService partNameService) {
        this.partNameService = partNameService;
    }

    @Autowired
    public void setBikeService(BikeService bikeService) {
        this.bikeService = bikeService;
    }

    public void save (Part part){
        partRepository.save(part);
    }

    public Part getById(Integer id){
        return partRepository.getReferenceById(id);
    }

    public List<Part> createParts(Bike bike){
        List<PartName> partNames = partNameService.getAll();
        List<Part> parts = new ArrayList<>();
        for (PartName partName:
             partNames) {
            Part part = new Part(partName.getName(), partName.getImportance(), partName.getDescription(), true, bike);
            parts.add(part);
            partRepository.save(part);
        }

        return parts;
    }

    public void deleteByName(String name) {
        partRepository.deleteByName(name);
    }

    public void saveForAll(PartName partName){
        Iterable<Bike> bikes = bikeService.getAllBikes();
        bikes.forEach(bike -> {
            bike.getParts()
                    .add(new Part(partName.getName(),
                            partName.getImportance(),
                            partName.getDescription(),
                            true,
                            bike));
            bikeService.save(bike);
        });
    }
}
