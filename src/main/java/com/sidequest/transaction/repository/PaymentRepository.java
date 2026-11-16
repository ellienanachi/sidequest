package com.sidequest.transaction.repository;

import com.sidequest.transaction.entity.Payment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PaymentRepository extends JpaRepository<Payment, Long> {

	List<Payment> findByAcceptanceId(Long acceptanceId);
}
