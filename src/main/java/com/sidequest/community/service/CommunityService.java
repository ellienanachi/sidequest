package com.sidequest.community.service;

import com.sidequest.community.dto.PostDto;
import com.sidequest.community.dto.ReviewDto;
import com.sidequest.community.repository.PostRepository;
import com.sidequest.community.repository.ReviewRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CommunityService {

	private final ReviewRepository reviewRepository;
	private final PostRepository postRepository;

	// TODO: leave a review after a completed booking, update profile ratings, messaging.
	@Transactional(readOnly = true)
	public List<ReviewDto> getAllReviews() {
		return reviewRepository.findAll().stream().map(ReviewDto::from).toList();
	}

	// TODO (extra: pitch wall): create posts, comments, upvotes.
	@Transactional(readOnly = true)
	public List<PostDto> getAllPosts() {
		return postRepository.findAll().stream().map(PostDto::from).toList();
	}
}
