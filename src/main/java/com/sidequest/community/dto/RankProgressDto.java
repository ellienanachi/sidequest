package com.sidequest.community.dto;

import com.sidequest.community.entity.Rank;

// Where a student is on the rank ladder, for the profile's rank badge and progress bar.
// e.g. Juan with 95 points: Squire, rank 3 of 6, 65 points to Knight, 18% of the way there.
public record RankProgressDto(
		String title,
		int level,              // 3 in "rank 3 of 6"
		int maxLevel,           // 6
		int points,
		String nextTitle,       // null at Legend (the top)
		Integer nextRankPoints, // points needed for the next rank, null at Legend
		int pointsToNext,       // 0 at Legend
		int progressPercent     // 0-100 between this rank and the next, 100 at Legend
) {
	public static RankProgressDto of(int points) {
		Rank rank = Rank.forPoints(points);
		Rank next = rank.next();
		int maxLevel = Rank.values().length;

		if (next == null) {
			return new RankProgressDto(rank.getTitle(), rank.getLevel(), maxLevel, points, null, null, 0, 100);
		}

		// Progress only counts this rank's step, e.g. Squire (80) to Knight (160) is an 80-point step.
		int step = next.getMinPoints() - rank.getMinPoints();
		int earnedInStep = points - rank.getMinPoints();
		int percent = earnedInStep * 100 / step; // whole numbers, rounded down

		return new RankProgressDto(rank.getTitle(), rank.getLevel(), maxLevel, points,
				next.getTitle(), next.getMinPoints(), next.getMinPoints() - points, percent);
	}
}
