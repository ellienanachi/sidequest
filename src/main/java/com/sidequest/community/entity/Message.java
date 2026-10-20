package com.sidequest.community.entity;

import com.sidequest.account.entity.User;
import com.sidequest.listing.entity.Listing;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "message")
@Getter
@Setter
@NoArgsConstructor
public class Message {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "message_id")
	private Long messageId;

	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "sender_id", nullable = false)
	private User sender;

	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "receiver_id", nullable = false)
	private User receiver;

	// Optional: a message can be about a specific listing.
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "listing_id")
	private Listing listing;

	@Column(nullable = false, length = 2000)
	private String content;

	@CreationTimestamp
	@Column(name = "sent_at", nullable = false, updatable = false)
	private LocalDateTime sentAt;

	@Column(name = "is_read", nullable = false)
	private Boolean isRead = false;
}
