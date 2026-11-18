package com.sidequest.community.dto;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

// Checks the profile's rank badge and progress bar numbers.
class RankProgressDtoTest {

	@Test
	void juanIsPartWayFromSquireToKnight() {
		RankProgressDto rank = RankProgressDto.of(95);
		assertEquals("Squire", rank.title());
		assertEquals(3, rank.level());
		assertEquals(6, rank.maxLevel());
		assertEquals("Knight", rank.nextTitle());
		assertEquals(160, rank.nextRankPoints());
		assertEquals(65, rank.pointsToNext());
		assertEquals(18, rank.progressPercent()); // 15 of the 80-point step, rounded down
	}

	@Test
	void reachingARankStartsItsBarAtZero() {
		RankProgressDto rank = RankProgressDto.of(30);
		assertEquals("Page", rank.title());
		assertEquals(0, rank.progressPercent());
		assertEquals(50, rank.pointsToNext()); // Squire at 80
	}

	@Test
	void legendHasNoNextRankAndAFullBar() {
		RankProgressDto rank = RankProgressDto.of(520);
		assertEquals("Legend", rank.title());
		assertNull(rank.nextTitle());
		assertNull(rank.nextRankPoints());
		assertEquals(0, rank.pointsToNext());
		assertEquals(100, rank.progressPercent());
	}
}
