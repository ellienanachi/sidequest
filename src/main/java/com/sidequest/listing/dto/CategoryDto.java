package com.sidequest.listing.dto;

import com.sidequest.listing.entity.Category;

public record CategoryDto(Long categoryId, String categoryName, String description) {

	public static CategoryDto from(Category category) {
		return new CategoryDto(category.getCategoryId(), category.getCategoryName(), category.getDescription());
	}
}
