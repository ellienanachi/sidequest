package com.sidequest.transaction.entity;

public enum PaymentPurpose {
	QUEST_REWARD, // held in escrow, linked to a quest acceptance
	GUILD_PASS    // subscription, no acceptance
}
