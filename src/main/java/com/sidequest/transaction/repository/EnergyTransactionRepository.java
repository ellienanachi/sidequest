package com.sidequest.transaction.repository;

import com.sidequest.transaction.entity.EnergyTransaction;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EnergyTransactionRepository extends JpaRepository<EnergyTransaction, Long> {

	List<EnergyTransaction> findByUserIdOrderByCreatedAtDesc(Long userId);
}
