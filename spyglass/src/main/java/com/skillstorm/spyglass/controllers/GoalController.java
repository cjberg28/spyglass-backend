package com.skillstorm.spyglass.controllers;

import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.Email;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.skillstorm.spyglass.models.Goal;
import com.skillstorm.spyglass.services.GoalService;

@RestController
@CrossOrigin("*")
@RequestMapping("/goals")
public class GoalController {
	
	private static final Logger logger = LoggerFactory.getLogger(GoalController.class);
	
	@Autowired
	private GoalService goalService;
	
	@GetMapping
	public ResponseEntity<List<Goal>> findAllGoals() {
		logger.trace("findAllGoals() called in GoalController.java");
		
		List<Goal> goals = goalService.findAllGoals();
		return ResponseEntity.ok(goals);
	}

	@GetMapping("/user/{email}")
	public ResponseEntity<List<Goal>> findByUser(@PathVariable @Valid @Email String email) {
		logger.trace("findByUser(" + email + ") called in GoalController.java");
		
		List<Goal> goals = goalService.findByUser(email);
		return ResponseEntity.ok(goals);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<Goal> findById(@PathVariable int id) {
		logger.trace("findById(" + id + ") called in GoalController.java");
		Goal goal = goalService.findById(id);
		return ResponseEntity.ok(goal);
	}
	
	@PostMapping
	public ResponseEntity<Goal> createGoal(@RequestBody Goal goal) {
		Goal newGoal = goalService.createGoal(goal);
		return new ResponseEntity<Goal>(newGoal, HttpStatus.CREATED);
	}
	
	@PutMapping
	public ResponseEntity<Boolean> updateGoal(@RequestBody Goal goal) {
		if (goalService.updateGoal(goal)) {
			return new ResponseEntity<Boolean>(true, HttpStatus.OK);
		}
		return new ResponseEntity<Boolean>(false, HttpStatus.BAD_REQUEST);
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<Boolean> deleteGoal(@PathVariable int id) {
		if (goalService.deleteGoal(id)) {
			return new ResponseEntity<Boolean>(true, HttpStatus.OK);
		}
		return new ResponseEntity<Boolean>(false, HttpStatus.BAD_REQUEST);
	}
}
