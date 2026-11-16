package com.sidequest.discovery.service;

import com.sidequest.quest.dto.QuestCardDto;
import com.sidequest.quest.entity.Quest;
import com.sidequest.quest.entity.QuestSize;
import com.sidequest.quest.entity.QuestStatus;
import com.sidequest.quest.repository.QuestRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.List;

import static com.sidequest.quest.repository.QuestFilters.*;

@Service
@RequiredArgsConstructor
public class DiscoveryService {

	private final QuestRepository questRepository;

	// Quest Board: only approved (OPEN) quests, newest first. Every filter is optional.
	// TODO (Objective 3): reward and deadline filters, saved quests, location preference,
	// category follows and alerts.
	@Transactional(readOnly = true)
	public List<QuestCardDto> searchBoard(String keyword, Long categoryId, QuestSize size, String location) {
		Specification<Quest> filter = hasStatus(QuestStatus.OPEN);
		if (StringUtils.hasText(keyword)) filter = filter.and(mentions(keyword.trim()));
		if (categoryId != null) filter = filter.and(inCategory(categoryId));
		if (size != null) filter = filter.and(hasSize(size));
		if (StringUtils.hasText(location)) filter = filter.and(atLocation(location.trim()));

		return questRepository.findAll(filter, Sort.by(Sort.Direction.DESC, "createdAt")).stream()
				.map(QuestCardDto::from)
				.toList();
	}
}
