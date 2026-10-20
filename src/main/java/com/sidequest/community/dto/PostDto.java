package com.sidequest.community.dto;

import com.sidequest.community.entity.Post;

import java.time.LocalDateTime;

// Extra: pitch wall.
public record PostDto(
		Long postId,
		Long userId,
		String content,
		Integer upvoteCount,
		LocalDateTime createdAt
) {
	public static PostDto from(Post post) {
		return new PostDto(
				post.getPostId(),
				post.getUser().getUserId(),
				post.getContent(),
				post.getUpvoteCount(),
				post.getCreatedAt());
	}
}
