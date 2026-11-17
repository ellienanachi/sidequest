package com.sidequest.community.service;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

// Only 4 and 5 star reviews earn rank points.
class GuildRankRulesTest {

	@Test
	void onlyGoodReviewsEarnPoints() {
		assertEquals(5, GuildRankRules.pointsForReview(5));
		assertEquals(5, GuildRankRules.pointsForReview(4));
		assertEquals(0, GuildRankRules.pointsForReview(3));
		assertEquals(0, GuildRankRules.pointsForReview(1));
	}
}
