package com.sidequest.quest.dto;

import com.sidequest.quest.entity.QuestSize;

import java.math.BigDecimal;

// The Quest Pricing table, so the frontend never hard-codes the ranges.
public record QuestSizeDto(
		QuestSize size,
		String typicalEffort,
		BigDecimal minReward,
		BigDecimal maxReward,
		int energyToPost
) {
	public static QuestSizeDto from(QuestSize size) {
		return new QuestSizeDto(size, size.getTypicalEffort(), size.getMinReward(),
				size.getMaxReward(), size.getEnergyToPost());
	}
}
