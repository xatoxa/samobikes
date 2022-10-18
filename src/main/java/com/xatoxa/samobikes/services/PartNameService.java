package com.xatoxa.samobikes.services;

import com.xatoxa.samobikes.entities.PartName;
import com.xatoxa.samobikes.repositories.PartNameRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PartNameService {
    private PartNameRepository partNameRepository;

    @Autowired
    public void setPartNameRepository(PartNameRepository partNameRepository) {
        this.partNameRepository = partNameRepository;
    }

    public List<PartName> getAll(){
        return partNameRepository.findAll();
    }
}
