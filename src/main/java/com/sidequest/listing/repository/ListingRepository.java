package com.sidequest.listing.repository;

import com.sidequest.listing.entity.Listing;
import com.sidequest.listing.entity.ListingStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ListingRepository extends JpaRepository<Listing, Long> {

	// Used by the discovery search placeholder.
	List<Listing> findByStatusAndTitleContainingIgnoreCase(ListingStatus status, String title);

	List<Listing> findByStatusAndCategoryCategoryId(ListingStatus status, Long categoryId);
}
