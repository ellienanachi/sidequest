package com.sidequest.transaction.entity;

import com.sidequest.account.entity.User;
import com.sidequest.quest.entity.Quest;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

// An adventurer taking a quest, from acceptance to completion.
@Entity
@Table(name = "quest_acceptances")
@Getter
@Setter
@NoArgsConstructor
public class QuestAcceptance {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "quest_id", nullable = false)
	private Quest quest;

	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "adventurer_id", nullable = false)
	private User adventurer;

	@Enumerated(EnumType.STRING)
	@Column(nullable = false, length = 20)
	private AcceptanceStatus status = AcceptanceStatus.ACCEPTED;

	@CreationTimestamp
	@Column(name = "accepted_at", nullable = false, updatable = false)
	private LocalDateTime acceptedAt;

	@Column(name = "completed_at")
	private LocalDateTime completedAt;
}
