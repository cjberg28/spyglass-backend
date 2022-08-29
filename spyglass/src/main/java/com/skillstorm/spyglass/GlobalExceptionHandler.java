package com.skillstorm.spyglass;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.skillstorm.spyglass.exceptions.GoalNotFoundException;
import com.skillstorm.spyglass.exceptions.UserNotFoundException;

@RestControllerAdvice
public class GlobalExceptionHandler {
	
	@ResponseStatus(HttpStatus.NOT_FOUND)
	@ExceptionHandler(UserNotFoundException.class)
	public String returnUserNotFound(UserNotFoundException e) {
		return e.getMessage();
	}
	
	@ResponseStatus(HttpStatus.NOT_FOUND)
	@ExceptionHandler(GoalNotFoundException.class)
	public String returnGoalNotFound(GoalNotFoundException e) {
		return e.getMessage();
	}
}
