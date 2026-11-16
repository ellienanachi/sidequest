package com.sidequest.transaction.entity;

import com.sidequest.account.entity.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

// Money for a quest reward (held in escrow, then released or refunded)
// or for a Guild Pass subscription.
@Entity
@Table(name = "payments")
@Getter
@Setter
@NoArgsConstructor
public class Payment {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "payer_id", nullable = false)
	private User payer;

	// Set for quest rewards, null for Guild Pass payments.
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "acceptance_id")
	private QuestAcceptance acceptance;

	@Enumerated(EnumType.STRING)
	@Column(nullable = false, length = 20)
	private PaymentPurpose purpose;

	// The reward (or ₱49 for Guild Pass), not counting the processing fee.
	@Column(nullable = false, precision = 10, scale = 2)
	private BigDecimal amount;

	// Paid by the quest giver on top of the amount (1.5% to 2.5%).
	@Column(name = "processing_fee", nullable = false, precision = 10, scale = 2)
	private BigDecimal processingFee = BigDecimal.ZERO;

	// SideQuest/s' 10% share, taken from the reward when it is released.
	@Column(nullable = false, precision = 10, scale = 2)
	private BigDecimal commission = BigDecimal.ZERO;

	@Enumerated(EnumType.STRING)
	@Column(nullable = false, length = 10)
	private PaymentMethod method;

	@Enumerated(EnumType.STRING)
	@Column(nullable = false, length = 20)
	private PaymentStatus status = PaymentStatus.PENDING;
}
