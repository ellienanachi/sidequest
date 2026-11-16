package com.sidequest.quest.repository;

import com.sidequest.quest.entity.Quest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

// JpaSpecificationExecutor lets the Quest Board combine optional filters (see QuestFilters).
public interface QuestRepository extends JpaRepository<Quest, Long>, JpaSpecificationExecutor<Quest> {
}
