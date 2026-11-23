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
}
