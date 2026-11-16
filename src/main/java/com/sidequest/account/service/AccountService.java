package com.sidequest.account.service;

import com.sidequest.account.dto.UserDto;
import com.sidequest.account.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AccountService {

	private final UserRepository userRepository;

	// TODO (Objective 1): register with CIT-U email, verify student ID, log in/out, edit profile.
	@Transactional(readOnly = true)
	public List<UserDto> getAllUsers() {
		LocalDateTime now = LocalDateTime.now();
		return userRepository.findAll().stream().map(user -> UserDto.from(user, now)).toList();
	}
}
