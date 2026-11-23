package com.sidequest.account.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

// A student ID check for a new account (Objective 1, Account Management).
// The student uploads a photo of their CIT-U ID; an admin approves or rejects it.
// The account stays PENDING until one is approved. A student can resubmit after a rejection,
// so one user can have many rows (many-to-one, not one-to-one).
@Entity
@Table(name = "student_verifications")
@Getter
@Setter
@NoArgsConstructor
public class StudentVerification {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	// Who submitted it. Creates student_verifications.user_id -> users.id.
	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "user_id", nullable = false)
	private User user;

	// Where the uploaded photo is stored. A plain link for now; file upload comes later.
	@Column(name = "id_photo_url", nullable = false, length = 500)
	private String idPhotoUrl;

	@Enumerated(EnumType.STRING)
	@Column(nullable = false, length = 20)
	private VerificationStatus status = VerificationStatus.PENDING;

	// The admin who checked it. Null while PENDING.
	// A second FK to users: student_verifications.reviewed_by -> users.id.
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "reviewed_by")
	private User reviewedBy;

	@CreationTimestamp
	@Column(name = "submitted_at", nullable = false, updatable = false)
	private LocalDateTime submittedAt;

	@Column(name = "reviewed_at")
	private LocalDateTime reviewedAt;

	// approve() and reject() are the operations in the Class Diagram. They only change this object
	// and its user; the service checks who may call them, and the transaction saves the result.

	// The ID is real: the account becomes ACTIVE and can use the quest board.
	public void approve(User admin, LocalDateTime now) {
		markReviewed(VerificationStatus.APPROVED, admin, now);
		user.setStatus(UserStatus.ACTIVE);
	}

	// The ID isn't accepted (blurry, not a CIT-U ID, ...). The account stays PENDING so the student can resubmit.
	public void reject(User admin, LocalDateTime now) {
		markReviewed(VerificationStatus.REJECTED, admin, now);
	}

	private void markReviewed(VerificationStatus newStatus, User admin, LocalDateTime now) {
		status = newStatus;
		reviewedBy = admin;
		reviewedAt = now;
	}
}
