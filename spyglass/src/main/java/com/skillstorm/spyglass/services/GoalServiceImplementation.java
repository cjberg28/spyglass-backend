package com.skillstorm.spyglass.services;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.skillstorm.spyglass.models.Goal;
import com.skillstorm.spyglass.repositories.GoalRepository;

@Service
@Transactional
public class GoalServiceImplementation implements GoalService {

	@Autowired
	private GoalRepository goalRepository;
	
	@Override
	public List<Goal> findAllGoals(String email) {
		return goalRepository.findByUserId(email);
	}

	@Override
	public Goal findById(int id) {
		Optional<Goal> goal = goalRepository.findById(id);
		if (goal.isPresent()) {//Goal exists.
			return goal.get();
		}
		return null;//Goal does not exist.
	}

	@Override
	public Goal createGoal(Goal goal) {
		return goalRepository.save(goal);
	}

	@Override
	public boolean updateGoal(Goal goal) {
		int result = goalRepository.updateGoal(goal.getId(),goal.getName(),goal.getDescription(),goal.getImageSrc(),goal.getTargetDate(),goal.getTargetAmount(),goal.getCurrentAmount(),goal.getUser().getEmail());
		if (result > 0) {//Changed row --> Update successful
			return true;
		}
		return false;
	}

	@Override
	public boolean deleteGoal(int id) {
		int result = goalRepository.deleteGoal(id);
		if (result > 0) {//Changed row --> Delete successful
			return true;
		}
		return false;
	}

}
