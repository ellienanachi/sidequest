package com.sidequest.account.dto;

import com.sidequest.account.entity.Role;
import com.sidequest.account.entity.Tier;
import com.sidequest.account.entity.User;
import com.sidequest.account.entity.UserStatus;
import com.sidequest.transaction.service.EnergyRules;

import java.time.LocalDateTime;

// Public view of a user. passwordHash is intentionally left out.
// energy is the live value, including recovery since it was last stored.
public record UserDto(
		Long id,
		String studentId,
		String email,
		String fullName,
		Role role,
		UserStatus status,
		Tier tier,
		int energy,
		int maxEnergy,
		long minutesUntilNextEnergy,
		String preferredLocation,
		LocalDateTime createdAt
) {
	public static UserDto from(User user, LocalDateTime now) {
		return new UserDto(
				user.getId(),
				user.getStudentId(),
				user.getEmail(),
				user.getFullName(),
				user.getRole(),
				user.getStatus(),
				user.getTier(),
				EnergyRules.currentEnergy(user, now),
				EnergyRules.maxEnergy(user, now),
				EnergyRules.minutesUntilNext(user, now),
				user.getPreferredLocation(),
				user.getCreatedAt());
	}
}
