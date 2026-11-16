package com.sidequest.transaction.service;

import com.sidequest.account.entity.Tier;
import com.sidequest.account.entity.User;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

// Checks the Energy System rules from the workbook.
class EnergyRulesTest {

	private static final LocalDateTime NOW = LocalDateTime.of(2026, 11, 16, 12, 0);

	@Test
	void recoversOneEnergyEveryThirtyMinutes() {
		assertEquals(4, EnergyRules.currentEnergy(4, NOW.minusMinutes(29), 10, NOW));
		assertEquals(5, EnergyRules.currentEnergy(4, NOW.minusMinutes(30), 10, NOW));
		assertEquals(7, EnergyRules.currentEnergy(4, NOW.minusMinutes(95), 10, NOW));
	}

	@Test
	void emptyFreeMeterIsFullAfterFiveHours() {
		assertEquals(9, EnergyRules.currentEnergy(0, NOW.minusMinutes(299), 10, NOW));
		assertEquals(10, EnergyRules.currentEnergy(0, NOW.minusHours(5), 10, NOW));
		assertEquals(10, EnergyRules.currentEnergy(0, NOW.minusHours(24), 10, NOW)); // stops at the cap
	}

	@Test
	void showsMinutesUntilNextEnergy() {
		assertEquals(24, EnergyRules.minutesUntilNext(8, NOW.minusMinutes(6), 10, NOW));
		assertEquals(0, EnergyRules.minutesUntilNext(10, NOW.minusMinutes(6), 10, NOW));
	}

	@Test
	void guildPassRaisesTheCapToTwenty() {
		User free = new User();
		assertEquals(10, EnergyRules.maxEnergy(free, NOW));

		User subscriber = new User();
		subscriber.setTier(Tier.GUILD_PASS);
		subscriber.setTierExpiresAt(NOW.plusDays(10));
		assertEquals(20, EnergyRules.maxEnergy(subscriber, NOW));

		subscriber.setTierExpiresAt(NOW.minusDays(1)); // expired pass
		assertEquals(10, EnergyRules.maxEnergy(subscriber, NOW));
	}
}
