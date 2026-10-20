package com.sidequest.admin.controller;

import com.sidequest.admin.dto.ReportDto;
import com.sidequest.admin.service.AdminService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/reports")
@RequiredArgsConstructor
public class AdminController {

	private final AdminService adminService;

	// TODO: admin-only once Spring Security is added.
	@GetMapping
	public List<ReportDto> getReports() {
		return adminService.getAllReports();
	}
}
