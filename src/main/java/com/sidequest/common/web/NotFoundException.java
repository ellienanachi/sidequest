package com.sidequest.common.web;

// Thrown when something asked for by id does not exist. Becomes a 404.
public class NotFoundException extends RuntimeException {

	public NotFoundException(String message) {
		super(message);
	}
}
