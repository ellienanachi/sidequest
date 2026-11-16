package com.sidequest.discovery.controller;

import com.sidequest.discovery.service.DiscoveryService;
import com.sidequest.quest.dto.QuestCardDto;
import com.sidequest.quest.entity.QuestSize;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/quests")
@RequiredArgsConstructor
public class DiscoveryController {

	private final DiscoveryService discoveryService;

	// The Quest Board, e.g. /api/quests?q=print&size=SMALL&categoryId=1&location=Library
	@GetMapping
	public List<QuestCardDto> searchBoard(
			@RequestParam(name = "q", required = false) String keyword,
			@RequestParam(name = "categoryId", required = false) Long categoryId,
			@RequestParam(name = "size", required = false) QuestSize size,
			@RequestParam(name = "location", required = false) String location) {
		return discoveryService.searchBoard(keyword, categoryId, size, location);
	}
}
