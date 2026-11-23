package com.sidequest.quest.entity;

import com.sidequest.account.entity.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.math.BigDecimal;
import java.time.LocalDateTime;

// A request for help posted by a quest giver.
@Entity
@Table(name = "quests")
@Getter
@Setter
@NoArgsConstructor
public class Quest {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	// The quest giver.
	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "poster_id", nullable = false)
	private User poster;

	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "category_id", nullable = false)
	private Category category;

	@Column(nullable = false, length = 150)
	private String title;

	@Column(nullable = false, length = 2000)
	private String description;

	@Enumerated(EnumType.STRING)
	@Column(nullable = false, length = 10)
	private QuestSize size;

	// Must be inside the size's range (Small 30-99, Medium 100-249, Large 250-500).
	@Column(nullable = false, precision = 10, scale = 2)
	private BigDecimal reward;

	@Column(length = 100)
	private String location;

	@Enumerated(EnumType.STRING)
	@Column(nullable = false, length = 20)
	private QuestStatus status = QuestStatus.PENDING;

	private LocalDateTime deadline;

	@CreationTimestamp
	@Column(name = "created_at", nullable = false, updatable = false)
	private LocalDateTime createdAt;

	// Quest Reposting (Objective 2): the earlier quest this one was copied from. Null for a new quest.
	// A self-reference: quests.reposted_from_id -> quests.id, so Workbench draws a line from quests to itself.
	// One quest can be reposted many times, so this is @ManyToOne.
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "reposted_from_id")
	private Quest repostedFrom;
}
