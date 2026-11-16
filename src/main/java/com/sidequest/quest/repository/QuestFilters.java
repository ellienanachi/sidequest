package com.sidequest.quest.repository;

import com.sidequest.quest.entity.Quest;
import com.sidequest.quest.entity.QuestSize;
import com.sidequest.quest.entity.QuestStatus;
import org.springframework.data.jpa.domain.Specification;

// Small building blocks for Quest Board searches. Combine them with .and(...).
// Each one is only added when its filter is set, so empty filters never reach the database.
public final class QuestFilters {

	private QuestFilters() {
	}

	public static Specification<Quest> hasStatus(QuestStatus status) {
		return (root, query, cb) -> cb.equal(root.get("status"), status);
	}

	public static Specification<Quest> inCategory(Long categoryId) {
		return (root, query, cb) -> cb.equal(root.get("category").get("id"), categoryId);
	}

	public static Specification<Quest> hasSize(QuestSize size) {
		return (root, query, cb) -> cb.equal(root.get("size"), size);
	}

	public static Specification<Quest> atLocation(String location) {
		return (root, query, cb) -> cb.equal(cb.lower(root.get("location")), location.toLowerCase());
	}

	// Matches the keyword in the title or description.
	public static Specification<Quest> mentions(String keyword) {
		String pattern = "%" + keyword.toLowerCase() + "%";
		return (root, query, cb) -> cb.or(
				cb.like(cb.lower(root.get("title")), pattern),
				cb.like(cb.lower(root.get("description")), pattern));
	}
}
