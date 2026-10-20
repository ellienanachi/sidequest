package com.sidequest.transaction.controller;

import com.sidequest.transaction.dto.BookingDto;
import com.sidequest.transaction.service.TransactionService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/bookings")
@RequiredArgsConstructor
public class TransactionController {

	private final TransactionService transactionService;

	@GetMapping
	public List<BookingDto> getBookings() {
		return transactionService.getAllBookings();
	}
}
