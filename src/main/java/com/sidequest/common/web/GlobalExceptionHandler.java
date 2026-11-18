package com.sidequest.common.web;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.resource.NoResourceFoundException;

import java.util.Map;
import java.util.stream.Collectors;

// Turns exceptions into simple JSON errors: {"error": "..."}.
@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler({NotFoundException.class, NoResourceFoundException.class})
	public ResponseEntity<Map<String, String>> handleNotFound(Exception ex) {
		String message = ex instanceof NotFoundException ? ex.getMessage() : "Not found";
		return error(HttpStatus.NOT_FOUND, message);
	}

	// e.g. /api/quests?size=HUGE or /api/quests/abc
	@ExceptionHandler(MethodArgumentTypeMismatchException.class)
	public ResponseEntity<Map<String, String>> handleBadParameter(MethodArgumentTypeMismatchException ex) {
		return error(HttpStatus.BAD_REQUEST, "Invalid value for '" + ex.getName() + "'");
	}

	// A rule was broken, e.g. reviewing a quest that isn't finished.
	@ExceptionHandler(BadRequestException.class)
	public ResponseEntity<Map<String, String>> handleBadRequest(BadRequestException ex) {
		return error(HttpStatus.BAD_REQUEST, ex.getMessage());
	}

	// @Valid failed on a request body, e.g. rating 6. Lists every field that failed:
	// "rating: must be less than or equal to 5".
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<Map<String, String>> handleInvalidBody(MethodArgumentNotValidException ex) {
		String message = ex.getBindingResult().getFieldErrors().stream()
				.map(fieldError -> fieldError.getField() + ": " + fieldError.getDefaultMessage())
				.sorted()
				.collect(Collectors.joining("; "));
		return error(HttpStatus.BAD_REQUEST, message);
	}

	// The body is missing or isn't valid JSON.
	@ExceptionHandler(HttpMessageNotReadableException.class)
	public ResponseEntity<Map<String, String>> handleUnreadableBody(HttpMessageNotReadableException ex) {
		return error(HttpStatus.BAD_REQUEST, "Request body is missing or is not valid JSON");
	}

	// It already exists, e.g. a second review of the same quest.
	@ExceptionHandler(ConflictException.class)
	public ResponseEntity<Map<String, String>> handleConflict(ConflictException ex) {
		return error(HttpStatus.CONFLICT, ex.getMessage());
	}

	// Anything else: log the details, but don't send internals to the browser.
	@ExceptionHandler(Exception.class)
	public ResponseEntity<Map<String, String>> handleException(Exception ex) {
		log.error("Unexpected error", ex);
		return error(HttpStatus.INTERNAL_SERVER_ERROR, "Something went wrong");
	}

	private ResponseEntity<Map<String, String>> error(HttpStatus status, String message) {
		return ResponseEntity.status(status).body(Map.of("error", message));
	}
}
