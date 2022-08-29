package com.skillstorm.spyglass.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.skillstorm.spyglass.exceptions.GoalNotFoundException;
import com.skillstorm.spyglass.models.Goal;
import com.skillstorm.spyglass.repositories.GoalRepository;

@Service
@Transactional(propagation = Propagation.REQUIRED)
public class GoalServiceImplementation implements GoalService {

	@Autowired
	private GoalRepository goalRepository;
	
	@Override
	public List<Goal> findByUser(String email) {
		List<Goal> goals = goalRepository.findByUserId(email);
		if (!goals.isEmpty()) {
			return goals;
		}
		throw new GoalNotFoundException("No goals exist for the provided user.");
	}

	@Override
	public Goal findById(int id) {
		Optional<Goal> goal = goalRepository.findById(id);
		if (goal.isPresent()) {//Goal exists.
			return goal.get();
		}
		throw new GoalNotFoundException("No goal exists for the provided id.");//Goal does not exist.
	}

	@Override
	public Goal createGoal(Goal goal) {
		return goalRepository.save(goal);
	}

	//Cannot change goal id.
	@Override
	public boolean updateGoal(Goal goal) {
		try {
			int result = goalRepository.updateGoal(goal.getId(),goal.getName(),goal.getDescription(),goal.getImageSrc(),goal.getTargetDate(),goal.getTargetAmount(),goal.getCurrentAmount(),goal.getUser().getEmail());
			if (result > 0) {//Changed row --> Update successful
				return true;
			}
			return false;
		} catch (Exception e) {//Can occur when entering a user email that doesn't exist.
			return false;
		}
	}

	@Override
	public boolean deleteGoal(int id) {
		int result = goalRepository.deleteGoal(id);
		if (result > 0) {//Changed row --> Delete successful
			return true;
		}
		return false;
	}

	@Override
	public List<Goal> findAllGoals() {
		List<Goal> goals = (List<Goal>) goalRepository.findAll();
		if (!goals.isEmpty()) {
			return goals;
		}
		throw new GoalNotFoundException("No goals currently exist in the database.");
	}

}
