package com.sidequest.quest.dto;

import com.sidequest.quest.entity.Quest;
import com.sidequest.quest.entity.QuestSize;

import java.math.BigDecimal;
import java.time.LocalDateTime;

// One card on the Quest Board.
public record QuestCardDto(
		Long id,
		String title,
		String description,
		QuestSize size,
		BigDecimal reward,
		String location,
		LocalDateTime deadline,
		Long categoryId,
		String categoryName,
		Long posterId,
		String posterName,
		LocalDateTime createdAt
) {
	// Call inside a transaction: poster and category are lazy.
	public static QuestCardDto from(Quest quest) {
		return new QuestCardDto(
				quest.getId(),
				quest.getTitle(),
				quest.getDescription(),
				quest.getSize(),
				quest.getReward(),
				quest.getLocation(),
				quest.getDeadline(),
				quest.getCategory().getId(),
				quest.getCategory().getName(),
				quest.getPoster().getId(),
				quest.getPoster().getFullName(),
				quest.getCreatedAt());
	}
}
