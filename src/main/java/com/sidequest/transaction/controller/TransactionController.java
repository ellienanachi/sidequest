package com.sidequest.transaction.controller;

import com.sidequest.transaction.dto.AcceptanceDto;
import com.sidequest.transaction.service.TransactionService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/acceptances")
@RequiredArgsConstructor
public class TransactionController {

	private final TransactionService transactionService;

	// TODO: only the giver's and adventurer's own acceptances once login exists.
	@GetMapping
	public List<AcceptanceDto> getAcceptances() {
		return transactionService.getAllAcceptances();
	}
}
