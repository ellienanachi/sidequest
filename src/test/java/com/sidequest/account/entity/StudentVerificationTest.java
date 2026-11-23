package com.sidequest.account.entity;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

// What approving and rejecting a student ID does to the verification and the account.
class StudentVerificationTest {

	private final LocalDateTime now = LocalDateTime.of(2026, 11, 23, 10, 0);
	private final User admin = new User();

	@Test
	void newVerificationStartsPending() {
		assertEquals(VerificationStatus.PENDING, new StudentVerification().getStatus());
	}

	@Test
	void approvingActivatesTheAccount() {
		StudentVerification verification = pendingFor(new User());

		verification.approve(admin, now);

		assertEquals(VerificationStatus.APPROVED, verification.getStatus());
		assertEquals(UserStatus.ACTIVE, verification.getUser().getStatus());
		assertSame(admin, verification.getReviewedBy());
		assertEquals(now, verification.getReviewedAt());
	}

	@Test
	void rejectingKeepsTheAccountPending() {
		StudentVerification verification = pendingFor(new User());

		verification.reject(admin, now);

		assertEquals(VerificationStatus.REJECTED, verification.getStatus());
		assertEquals(UserStatus.PENDING, verification.getUser().getStatus());
		assertSame(admin, verification.getReviewedBy());
	}

	private static StudentVerification pendingFor(User user) {
		StudentVerification verification = new StudentVerification();
		verification.setUser(user); // a new User starts PENDING
		return verification;
	}
}
