package com.sidequest.account.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

// The JSON body of POST /api/verifications, e.g.
// {"userId": 8, "idPhotoUrl": "/uploads/ids/25-1007-107.jpg"}
public record SubmitVerificationRequest(
		// Stand-in until login exists: then it comes from the logged-in user instead of the body.
		@NotNull Long userId,
		@NotBlank @Size(max = 500) String idPhotoUrl
) {
}
