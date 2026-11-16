package com.sidequest.quest.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

// A photo or detail image attached to a quest.
@Entity
@Table(name = "quest_photos")
@Getter
@Setter
@NoArgsConstructor
public class QuestPhoto {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "quest_id", nullable = false)
	private Quest quest;

	@Column(name = "image_url", nullable = false)
	private String imageUrl;
}
