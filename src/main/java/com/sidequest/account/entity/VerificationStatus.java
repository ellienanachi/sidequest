package com.sidequest.account.entity;

public enum VerificationStatus {
	PENDING,  // uploaded, waiting for an admin
	APPROVED, // the account becomes ACTIVE
	REJECTED  // e.g. blurry photo; the student can submit a new one
}
