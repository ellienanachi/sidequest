package com.sidequest.account.service;

import com.sidequest.account.dto.SubmitVerificationRequest;
import com.sidequest.account.dto.VerificationDto;
import com.sidequest.account.entity.Role;
import com.sidequest.account.entity.StudentVerification;
import com.sidequest.account.entity.User;
import com.sidequest.account.entity.UserStatus;
import com.sidequest.account.entity.VerificationStatus;
import com.sidequest.account.repository.StudentVerificationRepository;
import com.sidequest.account.repository.UserRepository;
import com.sidequest.common.web.BadRequestException;
import com.sidequest.common.web.ConflictException;
import com.sidequest.common.web.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

// Student ID verification (Objective 1, Account Management, Transaction 1).
@Service
@RequiredArgsConstructor
public class VerificationService {

	private final StudentVerificationRepository verificationRepository;
	private final UserRepository userRepository;

	// The student uploads their ID.
	@Transactional
	public VerificationDto submit(SubmitVerificationRequest request) {
		User user = findUser(request.userId());

		// Rule 1: only accounts still waiting to be verified can submit.
		if (user.getStatus() != UserStatus.PENDING) {
			throw new BadRequestException("This account doesn't need a student ID check");
		}
		// Rule 2: one at a time. After a rejection they can submit again.
		if (verificationRepository.existsByUserIdAndStatus(user.getId(), VerificationStatus.PENDING)) {
			throw new ConflictException("Your student ID is already waiting for review");
		}

		StudentVerification verification = new StudentVerification();
		verification.setUser(user);
		verification.setIdPhotoUrl(request.idPhotoUrl());
		return VerificationDto.from(verificationRepository.save(verification));
	}

	// The admin's queue (PENDING by default), oldest first.
	@Transactional(readOnly = true)
	public List<VerificationDto> getByStatus(VerificationStatus status) {
		return verificationRepository.findByStatusOrderBySubmittedAtAsc(status).stream()
				.map(VerificationDto::from)
				.toList();
	}

	@Transactional
	public VerificationDto approve(Long verificationId, Long adminId) {
		StudentVerification verification = findPending(verificationId);
		verification.approve(findAdmin(adminId), LocalDateTime.now());
		return VerificationDto.from(verification); // saved automatically when the transaction ends
	}

	@Transactional
	public VerificationDto reject(Long verificationId, Long adminId) {
		StudentVerification verification = findPending(verificationId);
		verification.reject(findAdmin(adminId), LocalDateTime.now());
		return VerificationDto.from(verification);
	}

	// Rule 3: a verification is reviewed once. Approving it twice, or rejecting an approved one, is a conflict.
	private StudentVerification findPending(Long verificationId) {
		StudentVerification verification = verificationRepository.findById(verificationId)
				.orElseThrow(() -> new NotFoundException("Verification " + verificationId + " not found"));
		if (verification.getStatus() != VerificationStatus.PENDING) {
			throw new ConflictException("This verification was already " + verification.getStatus().name().toLowerCase());
		}
		return verification;
	}

	// Rule 4: only admins review. Spring Security will enforce this later; for now the service checks it.
	private User findAdmin(Long adminId) {
		User admin = findUser(adminId);
		if (admin.getRole() != Role.ADMIN) {
			throw new BadRequestException("Only an admin can review a student ID");
		}
		return admin;
	}

	private User findUser(Long userId) {
		return userRepository.findById(userId)
				.orElseThrow(() -> new NotFoundException("User " + userId + " not found"));
	}
}
