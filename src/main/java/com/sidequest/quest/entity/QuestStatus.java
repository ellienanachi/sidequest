package com.sidequest.quest.entity;

// Where a quest is on the board. The step-by-step escrow progress
// (accepted, funded, marked done) lives on QuestAcceptance instead.
public enum QuestStatus {
	PENDING,     // waiting for admin review
	OPEN,        // approved and on the Quest Board
	IN_PROGRESS, // an adventurer accepted it
	COMPLETED,   // reward released
	CANCELLED,   // cancelled by the quest giver
	REJECTED,    // rejected by an admin (energy refunded)
	EXPIRED      // deadline passed with no one finishing it
}
