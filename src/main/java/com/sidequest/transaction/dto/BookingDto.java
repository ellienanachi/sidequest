package com.sidequest.transaction.dto;

import com.sidequest.transaction.entity.Booking;
import com.sidequest.transaction.entity.BookingStatus;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record BookingDto(
		Long bookingId,
		Long listingId,
		String listingTitle,
		Long buyerId,
		Long sellerId,
		LocalDateTime scheduledAt,
		String meetupSpot,
		String notes,
		BigDecimal totalAmount,
		BookingStatus status,
		LocalDateTime createdAt
) {
	public static BookingDto from(Booking booking) {
		return new BookingDto(
				booking.getBookingId(),
				booking.getListing().getListingId(),
				booking.getListing().getTitle(),
				booking.getBuyer().getUserId(),
				booking.getSeller().getUserId(),
				booking.getScheduledAt(),
				booking.getMeetupSpot(),
				booking.getNotes(),
				booking.getTotalAmount(),
				booking.getStatus(),
				booking.getCreatedAt());
	}
}
