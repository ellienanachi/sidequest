package com.sidequest.discovery.entity;

import com.sidequest.account.entity.User;
import com.sidequest.quest.entity.Category;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

// A category a student follows to get quest alerts. One follow per category per student.
@Entity
@Table(name = "category_follows",
		uniqueConstraints = @UniqueConstraint(columnNames = {"user_id", "category_id"}))
@Getter
@Setter
@NoArgsConstructor
public class CategoryFollow {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "user_id", nullable = false)
	private User user;

	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "category_id", nullable = false)
	private Category category;

	@CreationTimestamp
	@Column(name = "followed_at", nullable = false, updatable = false)
	private LocalDateTime followedAt;
}
