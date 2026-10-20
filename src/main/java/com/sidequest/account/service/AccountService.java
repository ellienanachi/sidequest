package com.sidequest.account.service;

import com.sidequest.account.dto.UserDto;
import com.sidequest.account.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AccountService {

	private final UserRepository userRepository;

	// TODO: registration, login, email verification, profile editing.
	@Transactional(readOnly = true)
	public List<UserDto> getAllUsers() {
		return userRepository.findAll().stream().map(UserDto::from).toList();
	}
}
