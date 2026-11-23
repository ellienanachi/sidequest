package com.sidequest.account.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

// A CIT-U student account. One user can be a quest giver, an adventurer, or both.
// Table is "users" because USER is a reserved word.
@Entity
@Table(name = "users")
@Getter
@Setter
@NoArgsConstructor
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "student_id", nullable = false, unique = true, length = 20)
	private String studentId;

	// CIT-U student email, used to log in.
	@Column(nullable = false, unique = true)
	private String email;

	// Never expose this in a DTO.
	@Column(name = "password_hash", nullable = false)
	private String passwordHash;

	@Column(name = "full_name", nullable = false, length = 150)
	private String fullName;

	@Enumerated(EnumType.STRING)
	@Column(nullable = false, length = 20)
	private Role role = Role.STUDENT;

	@Enumerated(EnumType.STRING)
	@Column(nullable = false, length = 20)
	private UserStatus status = UserStatus.PENDING;

	// Stored energy at energyUpdatedAt.
	@Column(nullable = false)
	private Integer energy = 10;

	@Column(name = "energy_updated_at", nullable = false)
	private LocalDateTime energyUpdatedAt = LocalDateTime.now();

	// e.g. "CCS Building". Used for the "Near you" board.
	@Column(name = "preferred_location", length = 100)
	private String preferredLocation;

	@Enumerated(EnumType.STRING)
	@Column(nullable = false, length = 20)
	private Tier tier = Tier.FREE;

	// When the Guild Pass runs out. Null for free students.
	@Column(name = "tier_expires_at")
	private LocalDateTime tierExpiresAt;

	@CreationTimestamp
	@Column(name = "created_at", nullable = false, updatable = false)
	private LocalDateTime createdAt;
}
