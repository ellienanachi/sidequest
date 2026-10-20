package com.sidequest.admin.dto;

import com.sidequest.admin.entity.Report;
import com.sidequest.admin.entity.ReportStatus;

import java.time.LocalDateTime;

public record ReportDto(
		Long reportId,
		Long reporterId,
		Long resolvedById,
		String targetType,
		Long targetId,
		String reason,
		ReportStatus status,
		LocalDateTime createdAt,
		LocalDateTime resolvedAt
) {
	public static ReportDto from(Report report) {
		return new ReportDto(
				report.getReportId(),
				report.getReporter().getUserId(),
				report.getResolvedBy() == null ? null : report.getResolvedBy().getUserId(),
				report.getTargetType(),
				report.getTargetId(),
				report.getReason(),
				report.getStatus(),
				report.getCreatedAt(),
				report.getResolvedAt());
	}
}
