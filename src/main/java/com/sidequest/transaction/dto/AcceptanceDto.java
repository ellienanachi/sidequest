package com.sidequest.transaction.dto;

import com.sidequest.transaction.entity.AcceptanceStatus;
import com.sidequest.transaction.entity.QuestAcceptance;

import java.time.LocalDateTime;

public record AcceptanceDto(
		Long id,
		Long questId,
		String questTitle,
		Long adventurerId,
		String adventurerName,
		AcceptanceStatus status,
		LocalDateTime acceptedAt,
		LocalDateTime completedAt
) {
	public static AcceptanceDto from(QuestAcceptance acceptance) {
		return new AcceptanceDto(
				acceptance.getId(),
				acceptance.getQuest().getId(),
				acceptance.getQuest().getTitle(),
				acceptance.getAdventurer().getId(),
				acceptance.getAdventurer().getFullName(),
				acceptance.getStatus(),
				acceptance.getAcceptedAt(),
				acceptance.getCompletedAt());
	}
}
