package com.sidequest.admin.service;

import com.sidequest.admin.dto.ReportDto;
import com.sidequest.admin.repository.ReportRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AdminService {

	private final ReportRepository reportRepository;

	// TODO: file a report, resolve/dismiss reports, moderate users and listings.
	@Transactional(readOnly = true)
	public List<ReportDto> getAllReports() {
		return reportRepository.findAll().stream().map(ReportDto::from).toList();
	}
}
