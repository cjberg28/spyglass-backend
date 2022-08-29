package com.skillstorm.spyglass.exceptions;

public class GoalNotFoundException extends RuntimeException {

	private static final long serialVersionUID = 5952416695615646319L;

	public GoalNotFoundException() {
		super();
	}
	
	public GoalNotFoundException(String message) {
		super(message);
	}
}
