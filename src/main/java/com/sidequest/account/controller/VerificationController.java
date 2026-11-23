package com.sidequest.account.controller;

import com.sidequest.account.dto.ReviewVerificationRequest;
import com.sidequest.account.dto.SubmitVerificationRequest;
import com.sidequest.account.dto.VerificationDto;
import com.sidequest.account.entity.VerificationStatus;
import com.sidequest.account.service.VerificationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/verifications")
@RequiredArgsConstructor
public class VerificationController {

	private final VerificationService verificationService;

	// POST /api/verifications -> 201 and the new PENDING verification.
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public VerificationDto submit(@Valid @RequestBody SubmitVerificationRequest request) {
		return verificationService.submit(request);
	}

	// GET /api/verifications (the PENDING queue), or ?status=APPROVED / REJECTED.
	// TODO: admin-only once Spring Security is added.
	@GetMapping
	public List<VerificationDto> getByStatus(@RequestParam(defaultValue = "PENDING") VerificationStatus status) {
		return verificationService.getByStatus(status);
	}

	// POST /api/verifications/{id}/approve -> the account becomes ACTIVE.
	@PostMapping("/{id}/approve")
	public VerificationDto approve(@PathVariable Long id, @Valid @RequestBody ReviewVerificationRequest request) {
		return verificationService.approve(id, request.adminId());
	}

	// POST /api/verifications/{id}/reject -> the account stays PENDING and the student can resubmit.
	@PostMapping("/{id}/reject")
	public VerificationDto reject(@PathVariable Long id, @Valid @RequestBody ReviewVerificationRequest request) {
		return verificationService.reject(id, request.adminId());
	}
}
