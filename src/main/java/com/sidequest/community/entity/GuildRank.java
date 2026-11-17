package com.sidequest.community.entity;

import com.sidequest.account.entity.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

// Rank points and title a student earns from completed quests and good ratings.
// The ladder is in Rank, the point values are in GuildRankRules.
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

	// Stored as text, as in the ERD, e.g. "Squire".
	@Column(name = "rank_title", nullable = false, length = 50)
	private String rankTitle = Rank.RECRUIT.getTitle();

	// From the class diagram: +addPoints(n).
	// Adds the points and moves the title up if a new rank was reached.
	public void addPoints(int points) {
		this.rankPoints += points;
		this.rankTitle = Rank.forPoints(this.rankPoints).getTitle();
	}
}
