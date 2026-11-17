package com.sidequest.community.entity;

// The guild rank ladder, lowest to highest. Agreed by the team on 2026-11-17
// (the workbook doesn't define it; Figma shows "Squire, rank 3 of 6").
// The order here matters: values() returns them lowest first.
public enum Rank {
	RECRUIT("Recruit", 0),
	PAGE("Page", 30),
	SQUIRE("Squire", 80),
	KNIGHT("Knight", 160),
	CHAMPION("Champion", 300),
	LEGEND("Legend", 500);

	private final String title;
	private final int minPoints; // points needed to reach this rank

	Rank(String title, int minPoints) {
		this.title = title;
		this.minPoints = minPoints;
	}

	public String getTitle() { return title; }
	public int getMinPoints() { return minPoints; }

	// 1 for Recruit up to 6 for Legend, for "rank 3 of 6".
	public int getLevel() {
		return ordinal() + 1;
	}

	// The rank after this one, or null for Legend (the top).
	public Rank next() {
		Rank[] ranks = values();
		return ordinal() + 1 < ranks.length ? ranks[ordinal() + 1] : null;
	}

	// The highest rank whose minimum the points have reached.
	// e.g. 95 points -> Squire (80), not yet Knight (160).
	public static Rank forPoints(int points) {
		Rank result = RECRUIT;
		for (Rank rank : values()) {
			if (points >= rank.minPoints) {
				result = rank;
			}
		}
		return result;
	}
}
