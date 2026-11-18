package com.sidequest.community.dto;

import com.sidequest.community.entity.Review;

import java.time.LocalDateTime;

// One review on a profile: who wrote it, for which quest, the stars and the comment.
public record ReviewDto(
		Long id,
		int rating,
		String comment,
		Long reviewerId,
		String reviewerName,
		Long questId,
		String questTitle,
		LocalDateTime createdAt
) {
	// Call inside a transaction: reviewer, acceptance and quest are lazy.
	public static ReviewDto from(Review review) {
		return new ReviewDto(
				review.getId(),
				review.getRating(),
				review.getComment(),
				review.getReviewer().getId(),
				review.getReviewer().getFullName(),
				review.getAcceptance().getQuest().getId(),
				review.getAcceptance().getQuest().getTitle(),
				review.getCreatedAt());
	}
}
