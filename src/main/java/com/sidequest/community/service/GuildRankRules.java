package com.sidequest.community.service;

// How guild rank points are earned (workbook, Objective 5 Module 3:
// "rank points and a rank title for completed quests and good ratings").
// The exact numbers were agreed by the team on 2026-11-17.
public final class GuildRankRules {

	public static final int COMPLETED_QUEST_POINTS = 10; // to the adventurer, per completed quest
	public static final int GOOD_REVIEW_POINTS = 5;      // to whoever receives a good review
	public static final int GOOD_REVIEW_MIN_RATING = 4;  // 4 or 5 stars counts as good

	private GuildRankRules() {
	}

	// Points earned for receiving a review with this rating (1 to 5).
	public static int pointsForReview(int rating) {
		return rating >= GOOD_REVIEW_MIN_RATING ? GOOD_REVIEW_POINTS : 0;
	}
}
