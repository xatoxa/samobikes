package com.xatoxa.samobikes.services;

import com.xatoxa.samobikes.entities.RepairHistory;
import com.xatoxa.samobikes.entities.User;
import com.xatoxa.samobikes.repositories.RepairHistoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
public class RepairHistoryService {
    public static final int ROWS_PER_PAGE = 10;

    private RepairHistoryRepository historyRepository;

    @Autowired
    public void setHistoryRepository(RepairHistoryRepository historyRepository) {
        this.historyRepository = historyRepository;
    }

    public Page<RepairHistory> getAllByPage(int pageNum){

        Pageable pageable = PageRequest.of(pageNum - 1, ROWS_PER_PAGE);

        return historyRepository.findAll(pageable);
    }

    public void save(RepairHistory repairHistory){
        historyRepository.save(repairHistory);
    }

    public void clean(){
        historyRepository.deleteAll();
    }

}
