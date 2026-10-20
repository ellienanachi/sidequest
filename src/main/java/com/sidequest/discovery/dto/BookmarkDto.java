package com.sidequest.discovery.dto;

import com.sidequest.discovery.entity.Bookmark;

import java.time.LocalDateTime;

public record BookmarkDto(
		Long bookmarkId,
		Long userId,
		Long listingId,
		String listingTitle,
		LocalDateTime createdAt
) {
	public static BookmarkDto from(Bookmark bookmark) {
		return new BookmarkDto(
				bookmark.getBookmarkId(),
				bookmark.getUser().getUserId(),
				bookmark.getListing().getListingId(),
				bookmark.getListing().getTitle(),
				bookmark.getCreatedAt());
	}
}
