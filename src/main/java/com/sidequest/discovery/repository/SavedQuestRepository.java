package com.sidequest.discovery.repository;

import com.sidequest.discovery.entity.SavedQuest;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SavedQuestRepository extends JpaRepository<SavedQuest, Long> {

	List<SavedQuest> findByUserId(Long userId);
}
