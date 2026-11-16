package com.sidequest.community.entity;

import com.sidequest.account.entity.User;
import com.sidequest.transaction.entity.QuestAcceptance;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

// Rating and comment left after a completed quest. Each side reviews once.
// The person being reviewed is the other side of the acceptance (giver or adventurer).
@Entity
@Table(name = "reviews",
		uniqueConstraints = @UniqueConstraint(columnNames = {"acceptance_id", "reviewer_id"}))
@Getter
@Setter
@NoArgsConstructor
public class Review {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "acceptance_id", nullable = false)
	private QuestAcceptance acceptance;

	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "reviewer_id", nullable = false)
	private User reviewer;

	// 1 to 5
	@Column(nullable = false)
	private Integer rating;

	@Column(length = 1000)
	private String comment;

	@CreationTimestamp
	@Column(name = "created_at", nullable = false, updatable = false)
	private LocalDateTime createdAt;
}
