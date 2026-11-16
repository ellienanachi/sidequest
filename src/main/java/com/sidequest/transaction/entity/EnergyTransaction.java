package com.sidequest.transaction.entity;

import com.sidequest.account.entity.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

// One change in a student's energy, kept as a history.
@Entity
@Table(name = "energy_transactions")
@Getter
@Setter
@NoArgsConstructor
public class EnergyTransaction {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "user_id", nullable = false)
	private User user;

	// Negative when spent, positive when gained.
	@Column(name = "change", nullable = false)
	private Integer change;

	@Enumerated(EnumType.STRING)
	@Column(nullable = false, length = 30)
	private EnergyReason reason;

	@CreationTimestamp
	@Column(name = "created_at", nullable = false, updatable = false)
	private LocalDateTime createdAt;
}
