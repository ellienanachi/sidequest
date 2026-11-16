package com.sidequest.transaction.service;

import com.sidequest.account.entity.Tier;
import com.sidequest.account.entity.User;

import java.time.Duration;
import java.time.LocalDateTime;

// Energy System rules from the workbook.
// Energy is stored with a timestamp; recovery since then is worked out on read,
// so no background job is needed.
public final class EnergyRules {

	public static final int FREE_MAX = 10;
	public static final int GUILD_PASS_MAX = 20;
	public static final int RECOVERY_MINUTES = 30; // +1 energy every 30 minutes, same for everyone
	public static final int ACCEPT_COST = 1;
	public static final int COMPLETION_BONUS = 1;  // adventurer gets 1 back after a completed quest
	// Posting costs are on QuestSize (1, 2 or 3).

	private EnergyRules() {
	}

	public static boolean hasActiveGuildPass(User user, LocalDateTime now) {
		return user.getTier() == Tier.GUILD_PASS
				&& (user.getTierExpiresAt() == null || user.getTierExpiresAt().isAfter(now));
	}

	public static int maxEnergy(User user, LocalDateTime now) {
		return hasActiveGuildPass(user, now) ? GUILD_PASS_MAX : FREE_MAX;
	}

	// Energy right now, including recovery since it was last stored.
	public static int currentEnergy(int stored, LocalDateTime storedAt, int max, LocalDateTime now) {
		if (stored >= max) {
			return stored; // recovery stops at the cap
		}
		long recovered = Duration.between(storedAt, now).toMinutes() / RECOVERY_MINUTES;
		return (int) Math.min(max, stored + Math.max(0, recovered));
	}

	public static int currentEnergy(User user, LocalDateTime now) {
		return currentEnergy(user.getEnergy(), user.getEnergyUpdatedAt(), maxEnergy(user, now), now);
	}

	// Minutes until the next +1, or 0 when the meter is full.
	public static long minutesUntilNext(int stored, LocalDateTime storedAt, int max, LocalDateTime now) {
		if (currentEnergy(stored, storedAt, max, now) >= max) {
			return 0;
		}
		long elapsed = Math.max(0, Duration.between(storedAt, now).toMinutes());
		return RECOVERY_MINUTES - (elapsed % RECOVERY_MINUTES);
	}

	public static long minutesUntilNext(User user, LocalDateTime now) {
		return minutesUntilNext(user.getEnergy(), user.getEnergyUpdatedAt(), maxEnergy(user, now), now);
	}
}
