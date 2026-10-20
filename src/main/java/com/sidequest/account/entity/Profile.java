package com.sidequest.account.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "profile")
@Getter
@Setter
@NoArgsConstructor
public class Profile {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "profile_id")
	private Long profileId;

	// One profile per user.
	@OneToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "user_id", nullable = false, unique = true)
	private User user;

	@Column(name = "display_name", length = 100)
	private String displayName;

	@Column(length = 1000)
	private String bio;

	@Column(name = "profile_picture")
	private String profilePicture;

	@Column(name = "buyer_rating", precision = 3, scale = 2)
	private BigDecimal buyerRating;

	@Column(name = "seller_rating", precision = 3, scale = 2)
	private BigDecimal sellerRating;

	@UpdateTimestamp
	@Column(name = "updated_at")
	private LocalDateTime updatedAt;
}
