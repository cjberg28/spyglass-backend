package com.skillstorm.spyglass.exceptions;

public class UserNotFoundException extends RuntimeException {

	private static final long serialVersionUID = -342755691338179792L;

	public UserNotFoundException() {
		super();
	}
	
	public UserNotFoundException(String message) {
		super(message);
	}
}
