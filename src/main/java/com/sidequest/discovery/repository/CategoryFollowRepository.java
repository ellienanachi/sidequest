package com.sidequest.discovery.repository;

import com.sidequest.discovery.entity.CategoryFollow;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CategoryFollowRepository extends JpaRepository<CategoryFollow, Long> {

	// Who to alert when a quest in this category is approved.
	List<CategoryFollow> findByCategoryId(Long categoryId);
}
