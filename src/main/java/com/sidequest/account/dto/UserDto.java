package com.sidequest.account.dto;

import com.sidequest.account.entity.Role;
import com.sidequest.account.entity.User;

import java.time.LocalDateTime;

// Public view of a user. passwordHash is intentionally left out.
public record UserDto(
		Long userId,
		String email,
		String studentIdNumber,
		String firstName,
		String lastName,
		Integer yearLevel,
		String program,
		Role role,
		Boolean isVerified,
		LocalDateTime createdAt
) {
	public static UserDto from(User user) {
		return new UserDto(
				user.getUserId(),
				user.getEmail(),
				user.getStudentIdNumber(),
				user.getFirstName(),
				user.getLastName(),
				user.getYearLevel(),
				user.getProgram(),
				user.getRole(),
				user.getIsVerified(),
				user.getCreatedAt());
	}
}
