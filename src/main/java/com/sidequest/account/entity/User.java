package com.sidequest.account.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

// Table is "users" because USER is a reserved word in H2.
@Entity
@Table(name = "users")
@Getter
@Setter
@NoArgsConstructor
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "user_id")
	private Long userId;

	@Column(nullable = false, unique = true)
	private String email;

	// Never expose this in a DTO.
	@Column(name = "password_hash", nullable = false)
	private String passwordHash;

	@Column(name = "student_id_number", nullable = false, unique = true, length = 20)
	private String studentIdNumber;

	@Column(name = "first_name", nullable = false, length = 100)
	private String firstName;

	@Column(name = "last_name", nullable = false, length = 100)
	private String lastName;

	@Column(name = "year_level")
	private Integer yearLevel;

	@Column(length = 100)
	private String program;

	@Enumerated(EnumType.STRING)
	@Column(nullable = false, length = 20)
	private Role role = Role.STUDENT;

	@Column(name = "is_verified", nullable = false)
	private Boolean isVerified = false;

	@CreationTimestamp
	@Column(name = "created_at", nullable = false, updatable = false)
	private LocalDateTime createdAt;
}
