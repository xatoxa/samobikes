package com.xatoxa.samobikes.services;

import com.xatoxa.samobikes.entities.History;
import com.xatoxa.samobikes.repositories.HistoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class HistoryService {
    public static final int ROWS_PER_PAGE = 10;

    private HistoryRepository historyRepository;

    @Autowired
    public void setHistoryRepository(HistoryRepository historyRepository) {
        this.historyRepository = historyRepository;
    }

    public Page<History> getAllByPage(int pageNum){

        Pageable pageable = PageRequest.of(pageNum - 1, ROWS_PER_PAGE);

        return historyRepository.findAll(pageable);
    }

    public void save(History history){
        historyRepository.save(history);
    }

    public void clean(){
        historyRepository.deleteAll();
    }

}
