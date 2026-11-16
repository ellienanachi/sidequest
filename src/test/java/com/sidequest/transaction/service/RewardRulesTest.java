package com.sidequest.transaction.service;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;

class RewardRulesTest {

	@Test
	void adventurerGetsNinetyPercent() {
		assertEquals(new BigDecimal("10.00"), RewardRules.commissionFor(new BigDecimal("100")));
		assertEquals(new BigDecimal("90.00"), RewardRules.payoutFor(new BigDecimal("100")));
		assertEquals(new BigDecimal("3.00"), RewardRules.commissionFor(new BigDecimal("30")));
		assertEquals(new BigDecimal("224.10"), RewardRules.payoutFor(new BigDecimal("249")));
	}
}
