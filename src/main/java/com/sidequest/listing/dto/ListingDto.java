package com.sidequest.listing.dto;

import com.sidequest.listing.entity.Listing;
import com.sidequest.listing.entity.ListingStatus;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record ListingDto(
		Long listingId,
		String title,
		String description,
		BigDecimal price,
		Integer deliveryDays,
		String availableDays,
		String meetupSpots,
		ListingStatus status,
		Long sellerId,
		String sellerName,
		Long categoryId,
		String categoryName,
		LocalDateTime createdAt
) {
	// Call inside a transaction: seller and category are lazy.
	public static ListingDto from(Listing listing) {
		return new ListingDto(
				listing.getListingId(),
				listing.getTitle(),
				listing.getDescription(),
				listing.getPrice(),
				listing.getDeliveryDays(),
				listing.getAvailableDays(),
				listing.getMeetupSpots(),
				listing.getStatus(),
				listing.getSeller().getUserId(),
				listing.getSeller().getFirstName() + " " + listing.getSeller().getLastName(),
				listing.getCategory().getCategoryId(),
				listing.getCategory().getCategoryName(),
				listing.getCreatedAt());
	}
}
