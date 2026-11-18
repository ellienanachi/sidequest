package com.sidequest.community.service;

import com.sidequest.account.entity.User;
import com.sidequest.account.repository.UserRepository;
import com.sidequest.common.web.BadRequestException;
import com.sidequest.common.web.ConflictException;
import com.sidequest.common.web.NotFoundException;
import com.sidequest.community.dto.CreateReviewRequest;
import com.sidequest.community.dto.ReviewDto;
import com.sidequest.community.entity.GuildRank;
import com.sidequest.community.entity.Review;
import com.sidequest.community.repository.GuildRankRepository;
import com.sidequest.community.repository.ReviewRepository;
import com.sidequest.transaction.entity.AcceptanceStatus;
import com.sidequest.transaction.entity.QuestAcceptance;
import com.sidequest.transaction.repository.QuestAcceptanceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

// Rate and review after a completed quest (Objective 5, Community Engagement).
@Service
@RequiredArgsConstructor
public class ReviewService {

	private final ReviewRepository reviewRepository;
	private final QuestAcceptanceRepository acceptanceRepository;
	private final UserRepository userRepository;
	private final GuildRankRepository guildRankRepository;

	// One transaction: the review and the rank points are saved together, or neither is.
	@Transactional
	public ReviewDto createReview(CreateReviewRequest request) {
		QuestAcceptance acceptance = acceptanceRepository.findById(request.acceptanceId())
				.orElseThrow(() -> new NotFoundException("Acceptance " + request.acceptanceId() + " not found"));
		User reviewer = userRepository.findById(request.reviewerId())
				.orElseThrow(() -> new NotFoundException("User " + request.reviewerId() + " not found"));

		// Rule 1: only finished quests can be reviewed.
		if (acceptance.getStatus() != AcceptanceStatus.COMPLETED) {
			throw new BadRequestException("You can only review a completed quest");
		}

		// Rule 2: only the quest giver and the adventurer can review, and each reviews the other.
		User reviewee = otherSide(acceptance, reviewer.getId());
		if (reviewee == null) {
			throw new BadRequestException("Only the quest giver and the adventurer can review this quest");
		}

		// Rule 3: one review each per quest. The database's unique constraint is the backup.
		if (reviewRepository.existsByAcceptanceIdAndReviewerId(acceptance.getId(), reviewer.getId())) {
			throw new ConflictException("You already reviewed this quest");
		}

		Review review = new Review();
		review.setAcceptance(acceptance);
		review.setReviewer(reviewer);
		review.setRating(request.rating());
		review.setComment(request.comment());
		reviewRepository.save(review);

		// +5 rank points for a 4 or 5 star review (GuildRankRules), to the person reviewed.
		int points = GuildRankRules.pointsForReview(request.rating());
		if (points > 0) {
			GuildRank rank = guildRankRepository.findByUserId(reviewee.getId()).orElseGet(() -> {
				GuildRank newRank = new GuildRank(); // every user should have one, but just in case
				newRank.setUser(reviewee);
				return newRank;
			});
			rank.addPoints(points);
			guildRankRepository.save(rank);
		}

		return ReviewDto.from(review);
	}

	// The person being reviewed: the adventurer if the quest giver is reviewing, and the other way round.
	// Null if the reviewer wasn't part of this quest.
	static User otherSide(QuestAcceptance acceptance, Long reviewerId) {
		User giver = acceptance.getQuest().getPoster();
		User adventurer = acceptance.getAdventurer();
		if (giver.getId().equals(reviewerId)) {
			return adventurer;
		}
		if (adventurer.getId().equals(reviewerId)) {
			return giver;
		}
		return null;
	}
}
