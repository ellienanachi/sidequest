package com.sidequest.community.entity;

import com.sidequest.account.entity.User;
import com.sidequest.transaction.entity.Booking;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

// Each person in a booking can review it once.
@Entity
@Table(name = "review",
		uniqueConstraints = @UniqueConstraint(columnNames = {"booking_id", "reviewer_id"}))
@Getter
@Setter
@NoArgsConstructor
public class Review {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "review_id")
	private Long reviewId;

	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "booking_id", nullable = false)
	private Booking booking;

	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "reviewer_id", nullable = false)
	private User reviewer;

	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "reviewee_id", nullable = false)
	private User reviewee;

	// 1 to 5. TODO: add @Min/@Max on the request DTO when create-review is built.
	@Column(nullable = false)
	private Integer rating;

	@Column(length = 1000)
	private String comment;

	@CreationTimestamp
	@Column(name = "created_at", nullable = false, updatable = false)
	private LocalDateTime createdAt;
}
