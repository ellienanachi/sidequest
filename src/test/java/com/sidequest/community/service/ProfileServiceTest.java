package com.sidequest.community.service;

import com.sidequest.community.dto.ReviewDto;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

// Checks the average star rating shown on a profile.
class ProfileServiceTest {

	@Test
	void averageIsRoundedToOneDecimal() {
		assertEquals(4.3, ProfileService.averageRating(List.of(review(5), review(4), review(4))));
		assertEquals(4.0, ProfileService.averageRating(List.of(review(5), review(4), review(3))));
	}

	@Test
	void noReviewsMeansNoAverage() {
		assertNull(ProfileService.averageRating(List.of()));
	}

	private static ReviewDto review(int rating) {
		return new ReviewDto(1L, rating, null, 2L, "Someone", 3L, "Some quest", null);
	}
}
