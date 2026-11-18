package com.sidequest.community.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

// The JSON body of POST /api/reviews, e.g.
// {"acceptanceId": 3, "reviewerId": 1, "rating": 5, "comment": "Great!"}
// The annotations are checked by @Valid in the controller; a failure becomes a 400.
public record CreateReviewRequest(
		@NotNull Long acceptanceId,
		// Who is writing the review. Stand-in until login exists (Objective 1):
		// then it comes from the logged-in user instead of the body.
		@NotNull Long reviewerId,
		@NotNull @Min(1) @Max(5) Integer rating,
		@Size(max = 1000) String comment
) {
}
