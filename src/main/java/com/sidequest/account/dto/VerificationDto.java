package com.sidequest.account.dto;

import com.sidequest.account.entity.StudentVerification;
import com.sidequest.account.entity.VerificationStatus;

import java.time.LocalDateTime;

// What the admin's verification screen shows. The student ID is included because the admin
// compares it with the photo; the email and password hash are left out.
public record VerificationDto(
		Long id,
		Long userId,
		String studentId,
		String fullName,
		String idPhotoUrl,
		VerificationStatus status,
		Long reviewedById, // null while PENDING
		LocalDateTime submittedAt,
		LocalDateTime reviewedAt
) {
	public static VerificationDto from(StudentVerification v) {
		return new VerificationDto(
				v.getId(),
				v.getUser().getId(),
				v.getUser().getStudentId(),
				v.getUser().getFullName(),
				v.getIdPhotoUrl(),
				v.getStatus(),
				v.getReviewedBy() == null ? null : v.getReviewedBy().getId(),
				v.getSubmittedAt(),
				v.getReviewedAt());
	}
}
