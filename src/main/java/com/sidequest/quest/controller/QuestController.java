package com.sidequest.quest.controller;

import com.sidequest.quest.dto.CategoryDto;
import com.sidequest.quest.dto.QuestDetailDto;
import com.sidequest.quest.dto.QuestSizeDto;
import com.sidequest.quest.service.QuestService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class QuestController {

	private final QuestService questService;

	@GetMapping("/categories")
	public List<CategoryDto> getCategories() {
		return questService.getCategories();
	}

	// Reward range and energy cost for each size.
	@GetMapping("/quest-sizes")
	public List<QuestSizeDto> getQuestSizes() {
		return questService.getQuestSizes();
	}

	@GetMapping("/quests/{id}")
	public QuestDetailDto getQuest(@PathVariable Long id) {
		return questService.getQuestDetails(id);
	}
}
