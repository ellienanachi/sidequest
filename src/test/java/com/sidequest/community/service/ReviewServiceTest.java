package com.sidequest.community.service;

import com.sidequest.account.entity.User;
import com.sidequest.quest.entity.Quest;
import com.sidequest.transaction.entity.QuestAcceptance;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

// Checks who gets reviewed: always the other side of the quest.
class ReviewServiceTest {

	private final User giver = user(1L);
	private final User adventurer = user(2L);
	private final QuestAcceptance acceptance = acceptance(giver, adventurer);

	@Test
	void questGiverReviewsTheAdventurer() {
		assertSame(adventurer, ReviewService.otherSide(acceptance, 1L));
	}

	@Test
	void adventurerReviewsTheQuestGiver() {
		assertSame(giver, ReviewService.otherSide(acceptance, 2L));
	}

	@Test
	void someoneOutsideTheQuestCannotReview() {
		assertNull(ReviewService.otherSide(acceptance, 3L));
	}

	private static User user(Long id) {
		User user = new User();
		user.setId(id);
		return user;
	}

	private static QuestAcceptance acceptance(User giver, User adventurer) {
		Quest quest = new Quest();
		quest.setPoster(giver);
		QuestAcceptance acceptance = new QuestAcceptance();
		acceptance.setQuest(quest);
		acceptance.setAdventurer(adventurer);
		return acceptance;
	}
}
