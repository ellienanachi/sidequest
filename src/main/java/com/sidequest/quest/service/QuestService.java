package com.sidequest.quest.service;

import com.sidequest.account.repository.ProfileRepository;
import com.sidequest.common.web.NotFoundException;
import com.sidequest.quest.dto.CategoryDto;
import com.sidequest.quest.dto.QuestDetailDto;
import com.sidequest.quest.dto.QuestSizeDto;
import com.sidequest.quest.entity.Quest;
import com.sidequest.quest.entity.QuestPhoto;
import com.sidequest.quest.entity.QuestSize;
import com.sidequest.quest.repository.CategoryRepository;
import com.sidequest.quest.repository.QuestPhotoRepository;
import com.sidequest.quest.repository.QuestRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;

@Service
@RequiredArgsConstructor
public class QuestService {

	private final QuestRepository questRepository;
	private final QuestPhotoRepository questPhotoRepository;
	private final CategoryRepository categoryRepository;
	private final ProfileRepository profileRepository;

	// TODO (Objective 2): post a quest (check reward range and energy, save as PENDING),
	// edit or cancel an open quest, repost a finished or expired quest.

	@Transactional(readOnly = true)
	public List<CategoryDto> getCategories() {
		return categoryRepository.findAll(Sort.by("name")).stream().map(CategoryDto::from).toList();
	}

	public List<QuestSizeDto> getQuestSizes() {
		return Arrays.stream(QuestSize.values()).map(QuestSizeDto::from).toList();
	}

	@Transactional(readOnly = true)
	public QuestDetailDto getQuestDetails(Long id) {
		Quest quest = questRepository.findById(id)
				.orElseThrow(() -> new NotFoundException("Quest " + id + " not found"));
		List<String> photoUrls = questPhotoRepository.findByQuestId(id).stream()
				.map(QuestPhoto::getImageUrl).toList();
		var profile = profileRepository.findByUserId(quest.getPoster().getId()).orElse(null);
		return QuestDetailDto.from(quest, profile, photoUrls);
	}
}
