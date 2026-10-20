package com.sidequest.listing.repository;

import com.sidequest.listing.entity.ListingPhoto;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ListingPhotoRepository extends JpaRepository<ListingPhoto, Long> {
}
