package com.sidequest.common.web;

// Thrown when the thing already exists, e.g. a second review of the same quest. Becomes a 409.
public class ConflictException extends RuntimeException {

	public ConflictException(String message) {
		super(message);
	}
}
