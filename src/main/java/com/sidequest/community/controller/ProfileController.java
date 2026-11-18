package com.sidequest.community.controller;

import com.sidequest.community.dto.ProfileDto;
import com.sidequest.community.service.ProfileService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

// Profile & Guild Rank page (Objective 5).
// Public, no login needed, so the page works without Objective 1 (login).
@RestController
@RequestMapping("/api/profiles")
@RequiredArgsConstructor
public class ProfileController {

	private final ProfileService profileService;

	// GET /api/profiles/1 -> Juan's profile, rank and reviews. Unknown id -> 404.
	@GetMapping("/{userId}")
	public ProfileDto getProfile(@PathVariable Long userId) {
		return profileService.getProfile(userId);
	}
}
