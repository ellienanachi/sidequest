package com.sidequest.community.entity;

import com.sidequest.account.entity.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

// Rank points and title a student earns from completed quests and good ratings.
// TODO (Objective 5): rank titles, point thresholds and how points are earned.
@Entity
@Table(name = "guild_ranks")
@Getter
@Setter
@NoArgsConstructor
public class GuildRank {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@OneToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "user_id", nullable = false, unique = true)
	private User user;

	@Column(name = "rank_points", nullable = false)
	private Integer rankPoints = 0;

	@Column(name = "rank_title", nullable = false, length = 50)
	private String rankTitle;
}
