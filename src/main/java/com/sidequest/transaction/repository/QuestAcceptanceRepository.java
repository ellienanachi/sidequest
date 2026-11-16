package com.sidequest.transaction.repository;

import com.sidequest.transaction.entity.QuestAcceptance;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface QuestAcceptanceRepository extends JpaRepository<QuestAcceptance, Long> {

	List<QuestAcceptance> findByQuestId(Long questId);

	List<QuestAcceptance> findByAdventurerId(Long adventurerId);
}
