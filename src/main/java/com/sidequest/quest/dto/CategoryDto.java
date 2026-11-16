package com.sidequest.quest.dto;

import com.sidequest.quest.entity.Category;

public record CategoryDto(Long id, String name) {

	public static CategoryDto from(Category category) {
		return new CategoryDto(category.getId(), category.getName());
	}
}
