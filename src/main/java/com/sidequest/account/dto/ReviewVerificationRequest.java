package com.sidequest.account.dto;

import jakarta.validation.constraints.NotNull;

// The JSON body of the approve and reject endpoints, e.g. {"adminId": 7}
public record ReviewVerificationRequest(
		// Stand-in until login exists: then it comes from the logged-in admin instead of the body.
		@NotNull Long adminId
) {
}
