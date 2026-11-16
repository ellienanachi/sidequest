package com.sidequest.transaction.entity;

public enum EnergyReason {
	POST_QUEST,      // -1, -2 or -3 by size
	ACCEPT_QUEST,    // -1
	QUEST_COMPLETED, // +1 for the adventurer
	QUEST_REJECTED,  // refund of the posting cost
	RECOVERY         // +1 every 30 minutes, up to the cap
}
