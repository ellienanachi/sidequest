package com.sidequest.discovery.service;

import com.sidequest.discovery.dto.BookmarkDto;
import com.sidequest.discovery.repository.BookmarkRepository;
import com.sidequest.listing.dto.ListingDto;
import com.sidequest.listing.entity.Listing;
import com.sidequest.listing.entity.ListingStatus;
import com.sidequest.listing.repository.ListingRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DiscoveryService {

	private final BookmarkRepository bookmarkRepository;
	private final ListingRepository listingRepository;

	// TODO: only return the logged-in user's bookmarks once auth exists.
	@Transactional(readOnly = true)
	public List<BookmarkDto> getAllBookmarks() {
		return bookmarkRepository.findAll().stream().map(BookmarkDto::from).toList();
	}

	// Placeholder search: active listings by category, or by title keyword.
	// TODO: combine filters (price range, meetup spot, available days), sorting, pagination.
	@Transactional(readOnly = true)
	public List<ListingDto> search(String keyword, Long categoryId) {
		List<Listing> results;
		if (categoryId != null) {
			results = listingRepository.findByStatusAndCategoryCategoryId(ListingStatus.ACTIVE, categoryId);
		} else {
			String q = (keyword == null) ? "" : keyword;
			results = listingRepository.findByStatusAndTitleContainingIgnoreCase(ListingStatus.ACTIVE, q);
		}
		return results.stream().map(ListingDto::from).toList();
	}
}
