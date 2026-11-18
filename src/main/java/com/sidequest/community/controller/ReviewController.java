package com.sidequest.community.controller;

import com.sidequest.community.dto.CreateReviewRequest;
import com.sidequest.community.dto.ReviewDto;
import com.sidequest.community.service.ReviewService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/reviews")
@RequiredArgsConstructor
public class ReviewController {

	private final ReviewService reviewService;

	// POST /api/reviews with a JSON body -> 201 Created and the new review.
	// @RequestBody reads the JSON into the record; @Valid checks its annotations first.
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public ReviewDto createReview(@Valid @RequestBody CreateReviewRequest request) {
		return reviewService.createReview(request);
	}
}
