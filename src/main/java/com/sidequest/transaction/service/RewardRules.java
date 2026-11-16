package com.sidequest.transaction.service;

import java.math.BigDecimal;
import java.math.RoundingMode;

// Quest Pricing rules from the workbook: SideQuest/s keeps 10% of the reward when it
// is released, the same for free and Guild Pass students.
public final class RewardRules {

	public static final BigDecimal COMMISSION_RATE = new BigDecimal("0.10");
	public static final BigDecimal GUILD_PASS_PRICE = new BigDecimal("49.00"); // per month
	// The processing fee (1.5% to 2.5%) depends on the method and is paid by the quest giver
	// on top of the reward. TODO: set the per-method rates once the PayMongo test account is ready.

	private RewardRules() {
	}

	public static BigDecimal commissionFor(BigDecimal reward) {
		return reward.multiply(COMMISSION_RATE).setScale(2, RoundingMode.HALF_UP);
	}

	// What the adventurer receives, e.g. ₱90 from a ₱100 quest.
	public static BigDecimal payoutFor(BigDecimal reward) {
		return reward.subtract(commissionFor(reward));
	}
}
