package com.sidequest.quest.entity;

import java.math.BigDecimal;

// Quest sizes from the workbook's Quest Pricing table.
// Rewards must fall inside the size's range; ₱500 is the overall cap.
public enum QuestSize {
	SMALL("Under 1 hour, such as printing a file or a quick errand", "30", "99", 1),
	MEDIUM("1 to 3 hours, such as a peer tutoring session", "100", "249", 2),
	LARGE("Half a day, such as helping set up an event", "250", "500", 3);

	private final String typicalEffort;
	private final BigDecimal minReward;
	private final BigDecimal maxReward;
	private final int energyToPost;

	QuestSize(String typicalEffort, String minReward, String maxReward, int energyToPost) {
		this.typicalEffort = typicalEffort;
		this.minReward = new BigDecimal(minReward);
		this.maxReward = new BigDecimal(maxReward);
		this.energyToPost = energyToPost;
	}

	public String getTypicalEffort() { return typicalEffort; }
	public BigDecimal getMinReward() { return minReward; }
	public BigDecimal getMaxReward() { return maxReward; }
	public int getEnergyToPost() { return energyToPost; }

	public boolean allowsReward(BigDecimal reward) {
		return reward != null && reward.compareTo(minReward) >= 0 && reward.compareTo(maxReward) <= 0;
	}
}
