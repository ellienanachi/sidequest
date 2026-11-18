package com.sidequest.common.web;

// Thrown when a request breaks a rule, e.g. reviewing a quest that isn't finished. Becomes a 400.
public class BadRequestException extends RuntimeException {

	public BadRequestException(String message) {
		super(message);
	}
}
