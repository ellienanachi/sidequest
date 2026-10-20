package com.sidequest.account.controller;

import com.sidequest.account.dto.UserDto;
import com.sidequest.account.service.AccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class AccountController {

	private final AccountService accountService;

	// TODO: restrict to admins once Spring Security is added.
	@GetMapping
	public List<UserDto> getUsers() {
		return accountService.getAllUsers();
	}
}
