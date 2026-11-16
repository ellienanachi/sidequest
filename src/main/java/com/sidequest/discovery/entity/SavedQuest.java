package com.sidequest.discovery.entity;

import com.sidequest.account.entity.User;
import com.sidequest.quest.entity.Quest;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

// A quest a student saved to view later. A student can save the same quest only once.
@Entity
@Table(name = "saved_quests",
		uniqueConstraints = @UniqueConstraint(columnNames = {"user_id", "quest_id"}))
@Getter
@Setter
@NoArgsConstructor
public class SavedQuest {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "user_id", nullable = false)
	private User user;

	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "quest_id", nullable = false)
	private Quest quest;

	@CreationTimestamp
	@Column(name = "saved_at", nullable = false, updatable = false)
	private LocalDateTime savedAt;
}
