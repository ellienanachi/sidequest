package com.sidequest.transaction.entity;

// The escrow steps for one adventurer taking one quest (Activity Diagrams 2 and 3).
public enum AcceptanceStatus {
	ACCEPTED,  // adventurer clicked Request!, 1 energy spent
	FUNDED,    // quest giver paid the reward into escrow
	DONE,      // adventurer marked the quest done
	COMPLETED, // giver confirmed, reward released minus commission
	DISPUTED,  // giver raised a dispute, admin decides
	CANCELLED,
	REFUNDED
}
