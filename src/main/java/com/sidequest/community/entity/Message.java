package com.sidequest.community.entity;

import com.sidequest.account.entity.User;
import com.sidequest.quest.entity.Quest;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

// Chat message between a quest giver and an adventurer about a quest.
// Matches the ERD. TODO (Objective 5): the ERD has no receiver or read flag yet,
// which Figma's "unread conversations" popup would need.
@Entity
@Table(name = "messages")
@Getter
@Setter
@NoArgsConstructor
public class Message {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "quest_id", nullable = false)
	private Quest quest;

	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "sender_id", nullable = false)
	private User sender;

	@Column(nullable = false, length = 2000)
	private String body;

	@CreationTimestamp
	@Column(name = "sent_at", nullable = false, updatable = false)
	private LocalDateTime sentAt;
}
