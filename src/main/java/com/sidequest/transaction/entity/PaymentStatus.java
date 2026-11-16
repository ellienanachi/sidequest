package com.sidequest.transaction.entity;

public enum PaymentStatus {
	PENDING,  // sent to the gateway, waiting for a result
	HELD,     // quest reward paid and held in escrow
	RELEASED, // reward paid out to the adventurer
	REFUNDED,
	PAID,     // Guild Pass subscription paid
	FAILED
}
