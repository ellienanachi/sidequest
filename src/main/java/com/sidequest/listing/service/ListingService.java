package com.sidequest.listing.service;

import com.sidequest.listing.dto.CategoryDto;
import com.sidequest.listing.dto.ListingDto;
import com.sidequest.listing.repository.CategoryRepository;
import com.sidequest.listing.repository.ListingRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ListingService {

	private final ListingRepository listingRepository;
	private final CategoryRepository categoryRepository;

	// TODO: create/edit/archive listings, photo upload, pagination.
	@Transactional(readOnly = true)
	public List<ListingDto> getAllListings() {
		return listingRepository.findAll().stream().map(ListingDto::from).toList();
	}

	@Transactional(readOnly = true)
	public List<CategoryDto> getAllCategories() {
		return categoryRepository.findAll().stream().map(CategoryDto::from).toList();
	}
}
