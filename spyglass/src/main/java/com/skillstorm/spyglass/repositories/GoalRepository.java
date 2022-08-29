package com.skillstorm.spyglass.repositories;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.skillstorm.spyglass.models.Goal;

@Repository
public interface GoalRepository extends CrudRepository<Goal,Integer> {
	
	public List<Goal> findByUserId(String email);
	
	@Modifying
	@Query("update Goal g set g.name = ?2, g.description = ?3, g.imageSrc = ?4, g.targetDate = ?5, g.targetAmount = ?6, g.currentAmount = ?7, g.userId = ?8 where g.id = ?1")
	public int updateGoal(int id, String name, String description, String imageSrc, LocalDate targetDate, float targetAmount, float currentAmount, String userId);

	@Modifying
	@Query("delete Goal g where g.id = ?1")
	public int deleteGoal(int id);
}
