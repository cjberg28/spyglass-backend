package com.skillstorm.spyglass.services;

import java.util.List;

import com.skillstorm.spyglass.models.Goal;

public interface GoalService {
	public List<Goal> findByUser(String email);

	public Goal findById(int id);

	public Goal createGoal(Goal goal);

	public boolean updateGoal(Goal goal);

	public boolean deleteGoal(int id);

	public List<Goal> findAllGoals();
}
