package com.sidequest.discovery.entity;

import com.sidequest.account.entity.User;
import com.sidequest.listing.entity.Listing;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

// A user can bookmark the same listing only once.
@Entity
@Table(name = "bookmark",
		uniqueConstraints = @UniqueConstraint(columnNames = {"user_id", "listing_id"}))
@Getter
@Setter
@NoArgsConstructor
public class Bookmark {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "bookmark_id")
	private Long bookmarkId;

	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "user_id", nullable = false)
	private User user;

	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "listing_id", nullable = false)
	private Listing listing;

	@CreationTimestamp
	@Column(name = "created_at", nullable = false, updatable = false)
	private LocalDateTime createdAt;
}
