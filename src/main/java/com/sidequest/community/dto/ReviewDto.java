package com.sidequest.community.dto;

import com.sidequest.community.entity.Review;

import java.time.LocalDateTime;

public record ReviewDto(
		Long reviewId,
		Long bookingId,
		Long reviewerId,
		Long revieweeId,
		Integer rating,
		String comment,
		LocalDateTime createdAt
) {
	public static ReviewDto from(Review review) {
		return new ReviewDto(
				review.getReviewId(),
				review.getBooking().getBookingId(),
				review.getReviewer().getUserId(),
				review.getReviewee().getUserId(),
				review.getRating(),
				review.getComment(),
				review.getCreatedAt());
	}
}
