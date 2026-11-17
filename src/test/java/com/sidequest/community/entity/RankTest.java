package com.sidequest.community.entity;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

// Checks the guild rank ladder: Recruit 0, Page 30, Squire 80, Knight 160, Champion 300, Legend 500.
class RankTest {

	@Test
	void pointsPickTheRightRank() {
		assertEquals(Rank.RECRUIT, Rank.forPoints(0));
		assertEquals(Rank.RECRUIT, Rank.forPoints(29));
		assertEquals(Rank.PAGE, Rank.forPoints(30));     // exactly on the line counts
		assertEquals(Rank.SQUIRE, Rank.forPoints(95));
		assertEquals(Rank.KNIGHT, Rank.forPoints(160));
		assertEquals(Rank.CHAMPION, Rank.forPoints(499));
		assertEquals(Rank.LEGEND, Rank.forPoints(500));
		assertEquals(Rank.LEGEND, Rank.forPoints(9999)); // no rank above Legend
	}

	@Test
	void levelsGoFromOneToSix() {
		assertEquals(1, Rank.RECRUIT.getLevel());
		assertEquals(3, Rank.SQUIRE.getLevel()); // "Squire, rank 3 of 6" from Figma
		assertEquals(6, Rank.LEGEND.getLevel());
		assertEquals(6, Rank.values().length);
	}

	@Test
	void nextRankIsTheOneAbove() {
		assertEquals(Rank.KNIGHT, Rank.SQUIRE.next());
		assertNull(Rank.LEGEND.next());
	}
}
