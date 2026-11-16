package com.sidequest.quest.entity;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

// Checks the Quest Pricing table from the workbook.
class QuestSizeTest {

	@Test
	void rewardRangesMatchTheWorkbook() {
		assertTrue(QuestSize.SMALL.allowsReward(new BigDecimal("30")));
		assertTrue(QuestSize.SMALL.allowsReward(new BigDecimal("99")));
		assertFalse(QuestSize.SMALL.allowsReward(new BigDecimal("100")));

		assertTrue(QuestSize.MEDIUM.allowsReward(new BigDecimal("100")));
		assertTrue(QuestSize.MEDIUM.allowsReward(new BigDecimal("249")));
		assertFalse(QuestSize.MEDIUM.allowsReward(new BigDecimal("250")));

		assertTrue(QuestSize.LARGE.allowsReward(new BigDecimal("500")));
		assertFalse(QuestSize.LARGE.allowsReward(new BigDecimal("501"))); // ₱500 cap
		assertFalse(QuestSize.SMALL.allowsReward(new BigDecimal("29")));
		assertFalse(QuestSize.SMALL.allowsReward(null));
	}

	@Test
	void postingCostsOneTwoOrThreeEnergy() {
		assertEquals(1, QuestSize.SMALL.getEnergyToPost());
		assertEquals(2, QuestSize.MEDIUM.getEnergyToPost());
		assertEquals(3, QuestSize.LARGE.getEnergyToPost());
	}
}
