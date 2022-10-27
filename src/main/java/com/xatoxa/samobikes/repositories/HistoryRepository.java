package com.xatoxa.samobikes.repositories;

import com.xatoxa.samobikes.entities.History;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HistoryRepository extends PagingAndSortingRepository<History, Integer> {
}
