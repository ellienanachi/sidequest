package com.sidequest.quest.dto;

import com.sidequest.account.entity.Profile;
import com.sidequest.quest.entity.Quest;
import com.sidequest.quest.entity.QuestSize;
import com.sidequest.quest.entity.QuestStatus;
import com.sidequest.transaction.entity.PaymentMethod;
import com.sidequest.transaction.service.EnergyRules;
import com.sidequest.transaction.service.RewardRules;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

// Everything the Quest Details screen shows.
public record QuestDetailDto(
		Long id,
		String title,
		String description,
		QuestSize size,
		QuestStatus status,
		BigDecimal reward,
		BigDecimal adventurerPayout, // reward minus the 10% commission
		List<PaymentMethod> paymentMethods,
		int energyToAccept,
		String location,
		LocalDateTime deadline,
		String categoryName,
		Long posterId,
		String posterName,
		String posterProgram,
		Integer posterYearLevel,
		List<String> photoUrls,
		LocalDateTime createdAt
) {
	// profile can be null if the poster has not filled it in yet.
	public static QuestDetailDto from(Quest quest, Profile profile, List<String> photoUrls) {
		return new QuestDetailDto(
				quest.getId(),
				quest.getTitle(),
				quest.getDescription(),
				quest.getSize(),
				quest.getStatus(),
				quest.getReward(),
				RewardRules.payoutFor(quest.getReward()),
				List.of(PaymentMethod.values()),
				EnergyRules.ACCEPT_COST,
				quest.getLocation(),
				quest.getDeadline(),
				quest.getCategory().getName(),
				quest.getPoster().getId(),
				quest.getPoster().getFullName(),
				profile == null ? null : profile.getProgram(),
				profile == null ? null : profile.getYearLevel(),
				photoUrls,
				quest.getCreatedAt());
	}
}
