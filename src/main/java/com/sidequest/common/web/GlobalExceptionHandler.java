package com.sidequest.common.web;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.resource.NoResourceFoundException;

import java.util.Map;

// Turns exceptions into simple JSON errors: {"error": "..."}.
// TODO: add validation error details once create/update endpoints exist.
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
