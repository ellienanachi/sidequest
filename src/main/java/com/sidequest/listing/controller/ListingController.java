package com.sidequest.listing.controller;

import com.sidequest.listing.dto.CategoryDto;
import com.sidequest.listing.dto.ListingDto;
import com.sidequest.listing.service.ListingService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class ListingController {

	private final ListingService listingService;

	@GetMapping("/listings")
	public List<ListingDto> getListings() {
		return listingService.getAllListings();
	}

	@GetMapping("/categories")
	public List<CategoryDto> getCategories() {
		return listingService.getAllCategories();
	}
}
