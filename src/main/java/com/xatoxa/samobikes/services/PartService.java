package com.xatoxa.samobikes.services;

import com.xatoxa.samobikes.entities.Part;
import com.xatoxa.samobikes.repositories.PartRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PartService {
    private PartRepository partRepository;

    @Autowired
    public void setPartRepository(PartRepository partRepository){
        this.partRepository = partRepository;
    }

    public void add (Part part){
        partRepository.save(part);
    }

    public Part getById(Integer id){
        return partRepository.getReferenceById(id);
    }


}
