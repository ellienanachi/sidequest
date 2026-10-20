package com.sidequest.discovery.controller;

import com.sidequest.discovery.dto.BookmarkDto;
import com.sidequest.discovery.service.DiscoveryService;
import com.sidequest.listing.dto.ListingDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class DiscoveryController {

	private final DiscoveryService discoveryService;

	@GetMapping("/bookmarks")
	public List<BookmarkDto> getBookmarks() {
		return discoveryService.getAllBookmarks();
	}

	// e.g. /api/search?q=tutor or /api/search?categoryId=1
	@GetMapping("/search")
	public List<ListingDto> search(@RequestParam(name = "q", required = false) String keyword,
			@RequestParam(name = "categoryId", required = false) Long categoryId) {
		return discoveryService.search(keyword, categoryId);
	}
}
