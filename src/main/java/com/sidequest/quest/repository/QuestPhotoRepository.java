package com.sidequest.quest.repository;

import com.sidequest.quest.entity.QuestPhoto;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface QuestPhotoRepository extends JpaRepository<QuestPhoto, Long> {

	List<QuestPhoto> findByQuestId(Long questId);
}
