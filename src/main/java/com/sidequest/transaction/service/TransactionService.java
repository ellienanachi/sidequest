package com.sidequest.transaction.service;

import com.sidequest.transaction.dto.BookingDto;
import com.sidequest.transaction.repository.BookingRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TransactionService {

	private final BookingRepository bookingRepository;

	// TODO: create/confirm/cancel bookings, record payments, send notifications.
	@Transactional(readOnly = true)
	public List<BookingDto> getAllBookings() {
		return bookingRepository.findAll().stream().map(BookingDto::from).toList();
	}
}
