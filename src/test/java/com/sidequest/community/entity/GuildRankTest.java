package com.sidequest.community.entity;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

// Checks that adding points keeps the title in step.
class GuildRankTest {

	@Test
	void newStudentsStartAsRecruits() {
		GuildRank rank = new GuildRank();
		assertEquals(0, rank.getRankPoints());
		assertEquals("Recruit", rank.getRankTitle());
	}

	@Test
	void addingPointsCanMoveTheTitleUp() {
		GuildRank rank = new GuildRank();
		rank.addPoints(75);
		assertEquals("Page", rank.getRankTitle());

		rank.addPoints(5); // 80 points: just reached Squire
		assertEquals(80, rank.getRankPoints());
		assertEquals("Squire", rank.getRankTitle());
	}
}
