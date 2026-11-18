package com.sidequest.community.service;

import com.sidequest.account.entity.Profile;
import com.sidequest.account.entity.User;
import com.sidequest.account.repository.ProfileRepository;
import com.sidequest.account.repository.UserRepository;
import com.sidequest.common.web.NotFoundException;
import com.sidequest.community.dto.ProfileDto;
import com.sidequest.community.dto.RankProgressDto;
import com.sidequest.community.dto.ReviewDto;
import com.sidequest.community.entity.GuildRank;
import com.sidequest.community.repository.GuildRankRepository;
import com.sidequest.community.repository.ReviewRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

// Builds a student's public profile from four tables: users, profiles, guild_ranks and reviews.
@Service
@RequiredArgsConstructor
public class ProfileService {

	private final UserRepository userRepository;
	private final ProfileRepository profileRepository;
	private final GuildRankRepository guildRankRepository;
	private final ReviewRepository reviewRepository;

	// readOnly: this only reads, so Hibernate can skip checking for changes.
	@Transactional(readOnly = true)
	public ProfileDto getProfile(Long userId) {
		User user = userRepository.findById(userId)
				.orElseThrow(() -> new NotFoundException("User " + userId + " not found"));

		// Every seeded user has both, but don't crash if one is missing.
		Profile profile = profileRepository.findByUserId(userId).orElse(null);
		int points = guildRankRepository.findByUserId(userId).map(GuildRank::getRankPoints).orElse(0);

		List<ReviewDto> reviews = reviewRepository.findReceivedBy(userId).stream()
				.map(ReviewDto::from)
				.toList();

		return new ProfileDto(
				user.getId(),
				user.getFullName(),
				user.getRole(),
				profile != null ? profile.getProgram() : null,
				profile != null ? profile.getYearLevel() : null,
				user.getPreferredLocation(),
				RankProgressDto.of(points),
				averageRating(reviews),
				reviews.size(),
				reviews);
	}

	// Average stars rounded to 1 decimal, e.g. 4.33 -> 4.3. Null when there are no reviews.
	static Double averageRating(List<ReviewDto> reviews) {
		if (reviews.isEmpty()) {
			return null;
		}
		double average = reviews.stream().mapToInt(ReviewDto::rating).average().orElse(0);
		return Math.round(average * 10) / 10.0;
	}
}
