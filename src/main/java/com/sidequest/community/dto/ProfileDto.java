package com.sidequest.community.dto;

import com.sidequest.account.entity.Role;

import java.util.List;

// Everything the Profile & Guild Rank page needs, in one response.
// Public info only: no email, student ID or password hash.
public record ProfileDto(
		Long userId,
		String fullName,
		Role role,
		String program,          // e.g. "BSIT", null for the admin
		Integer yearLevel,
		String preferredLocation,
		RankProgressDto rank,
		Double averageRating,    // e.g. 4.0, null when there are no reviews yet
		int reviewCount,
		List<ReviewDto> reviews  // reviews received, newest first
) {
}
