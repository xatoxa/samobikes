package com.xatoxa.samobikes.repositories;

import com.xatoxa.samobikes.entities.RepairHistory;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RepairHistoryRepository extends PagingAndSortingRepository<RepairHistory, Integer> {
}
