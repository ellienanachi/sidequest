package com.sidequest.transaction.service;

import com.sidequest.transaction.dto.AcceptanceDto;
import com.sidequest.transaction.repository.QuestAcceptanceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TransactionService {

	private final QuestAcceptanceRepository acceptanceRepository;

	// TODO (Objective 4): accept a quest (1 energy), fund it into escrow through PaymentGateway,
	// mark done, confirm and release minus commission (+1 energy back), cancel or refund,
	// Guild Pass subscription and ads.
	@Transactional(readOnly = true)
	public List<AcceptanceDto> getAllAcceptances() {
		return acceptanceRepository.findAll().stream().map(AcceptanceDto::from).toList();
	}
}
