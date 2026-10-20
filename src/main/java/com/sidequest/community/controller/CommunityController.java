package com.sidequest.community.controller;

import com.sidequest.community.dto.PostDto;
import com.sidequest.community.dto.ReviewDto;
import com.sidequest.community.service.CommunityService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class CommunityController {

	private final CommunityService communityService;

	@GetMapping("/reviews")
	public List<ReviewDto> getReviews() {
		return communityService.getAllReviews();
	}

	// Extra: pitch wall.
	@GetMapping("/posts")
	public List<PostDto> getPosts() {
		return communityService.getAllPosts();
	}
}
