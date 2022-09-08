package com.skillstorm.spyglass.services;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyFloat;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.time.LocalDate;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import com.skillstorm.spyglass.exceptions.GoalNotFoundException;
import com.skillstorm.spyglass.models.Goal;
import com.skillstorm.spyglass.models.User;
import com.skillstorm.spyglass.repositories.GoalRepository;

@SpringBootTest
@ActiveProfiles("dev")
public class GoalServiceTest {
	
	@InjectMocks //Injects fake dependencies - This is what you should be testing in this file
	GoalServiceImplementation goalService;
	
	@Mock //Injects an entire fake object
	GoalRepository goalRepository;
	
	@Test
	@DisplayName("findById() - Goal Found") //User should be already authenticated at this stage
	public void testFindByIdWithGoalFound() throws Exception {
		//1. Make a mock goal to compare to.
		Goal mockGoal = new Goal(1,"Test Goal 1", "Test Desc", "example.com/image", LocalDate.of(2052, 1, 1), 100.0f, 0.0f);
		
		//2. Have the fake repository call return the mock goal, as if it were successful.
		Mockito.when(goalRepository.findById(anyInt())).thenReturn(Optional.of(mockGoal));
		
		//3. Make the actual service call to test the method.
		Goal foundGoal = goalService.findById(mockGoal.getId());
		
		//4. Compare the found goal and original mock goal field by field to determine equality.
		assertThat(foundGoal).usingRecursiveComparison().isEqualTo(mockGoal);
		
		//5. Verify that findById() was "called" once by the stub.
		verify(goalRepository, times(1)).findById(anyInt());
	}
	
	@Test
	@DisplayName("findById() - Goal Not Found")
	public void testFindByIdWithGoalNotFound() throws Exception {
		//1. Make a mock goal to compare to.
		Goal mockGoal = new Goal(1,"Test Goal 1", "Test Desc", "example.com/image", LocalDate.of(2052, 1, 1), 100.0f, 0.0f);
		
		//2. Have the fake repository call return an empty goal object, as if it were a failure.
		Mockito.when(goalRepository.findById(anyInt())).thenReturn(Optional.empty());
		
		//4. Expect the search to fail and throw an exception.
		assertThrows(GoalNotFoundException.class, () -> {
			//3. Make the actual service call to test the method.
			Goal foundGoal = goalService.findById(mockGoal.getId());
		});
		
		//5. Verify that findById() was "called" once by the stub.
		verify(goalRepository, times(1)).findById(anyInt());
	}
	
	@Test
	@DisplayName("findByUser() - Goals Found")
	public void testFindByUserWithGoalsFound() throws Exception {
		List<Goal> mockGoals = new LinkedList<Goal>();
		mockGoals.add(new Goal(1,"Test Goal 1", "Test Desc", "example.com/image", LocalDate.of(2052, 1, 1), 100.0f, 0.0f));
		mockGoals.add(new Goal(2,"Test Goal 2", "Test Desc", "example.com/image", LocalDate.of(2052, 1, 1), 100.0f, 0.0f));
		
		Mockito.when(goalRepository.findByUserId(any())).thenReturn(mockGoals);
		
		List<Goal> foundGoals = goalService.findByUser("jtester@skillstorm.com");
		
		assertThat(foundGoals).usingRecursiveComparison().isEqualTo(mockGoals);
		
		verify(goalRepository, times(1)).findByUserId(any());
	}
	
	@Test
	@DisplayName("findByUser() - Goals Not Found")
	public void testFindByUserWithGoalsNotFound() throws Exception {
		Mockito.when(goalRepository.findByUserId(any())).thenReturn(new LinkedList<Goal>());
		
		assertThrows(GoalNotFoundException.class, () -> {
			List<Goal> foundGoals = goalService.findByUser("jtester@skillstorm.com");
		});
		
		verify(goalRepository, times(1)).findByUserId(any());
	}
	
	@Test
	@DisplayName("createGoal()")
	public void testCreateGoal() throws Exception {
		//Unsaved goals do not have an id, so it is automatically 0.
		Goal mockGoal = new Goal("Test Goal 1", "Test Desc", "example.com/image", LocalDate.of(2052, 1, 1), 100.0f, 0.0f);
		Goal newGoal = new Goal(1, "Test Goal 1", "Test Desc", "example.com/image", LocalDate.of(2052, 1, 1), 100.0f, 0.0f);
		
		Mockito.when(goalRepository.save(any())).thenReturn(newGoal);
		
		Goal createdGoal = goalService.createGoal(mockGoal);
		
		assertThat(createdGoal.getId()).isNotEqualTo(0);//0 means not created
		assertThat(createdGoal).usingRecursiveComparison().ignoringFields("id").isEqualTo(mockGoal);

		verify(goalRepository, times(1)).save(any());
	}
	
	@Test
	@DisplayName("updateGoal() - Goal Found")
	public void testUpdateGoalWithGoalFound() throws Exception {
		Goal mockGoal = new Goal(1, "Test Goal 1", "Test Desc", "example.com/image", LocalDate.of(2052, 1, 1), 100.0f, 0.0f);
		mockGoal.setUser(new User("jtester@skillstorm.com", "Jonathan", "Tester", LocalDate.of(2000, 1, 1)));
		
		//1 - Successful update
		Mockito.when(goalRepository.updateGoal(anyInt(),any(),any(),any(),any(),anyFloat(),anyFloat(),any())).thenReturn(1);
		
		boolean result = goalService.updateGoal(mockGoal);
		
		assertThat(result).isTrue();

		verify(goalRepository, times(1)).updateGoal(anyInt(),any(),any(),any(),any(),anyFloat(),anyFloat(),any());
	}
	
	@Test
	@DisplayName("updateGoal() - Goal Not Found")
	public void testUpdateGoalWithGoalNotFound() throws Exception {
		Goal mockGoal = new Goal(1, "Test Goal 1", "Test Desc", "example.com/image", LocalDate.of(2052, 1, 1), 100.0f, 0.0f);
		mockGoal.setUser(new User("jtester@skillstorm.com", "Jonathan", "Tester", LocalDate.of(2000, 1, 1)));
		
		//0 - Failed update
		Mockito.when(goalRepository.updateGoal(anyInt(),any(),any(),any(),any(),anyFloat(),anyFloat(),any())).thenReturn(0);
		
		boolean result = goalService.updateGoal(mockGoal);
		
		assertThat(result).isFalse();

		verify(goalRepository, times(1)).updateGoal(anyInt(),any(),any(),any(),any(),anyFloat(),anyFloat(),any());
	}
	
	@Test
	@DisplayName("updateGoal() - Exception Thrown")
	public void testUpdateGoalWithExceptionThrown() throws Exception {
		Goal mockGoal = new Goal(1, "Test Goal 1", "Test Desc", "example.com/image", LocalDate.of(2052, 1, 1), 100.0f, 0.0f);
		
		//User used in update does not exist, so exception is thrown
		boolean result = goalService.updateGoal(mockGoal);
		
		assertThat(result).isFalse();
	}
	
	@Test
	@DisplayName("deleteGoal() - Goal Found")
	public void testDeleteGoalWithGoalFound() throws Exception {
		int id = 1;
		
		//1 - Successful delete
		Mockito.when(goalRepository.deleteGoal(anyInt())).thenReturn(1);
		
		boolean result = goalService.deleteGoal(id);
		
		assertThat(result).isTrue();

		verify(goalRepository, times(1)).deleteGoal(anyInt());
	}
	
	@Test
	@DisplayName("deleteGoal() - Goal Not Found")
	public void testDeleteGoalWithGoalNotFound() throws Exception {
		int id = 1;
		
		//0 - Failed delete
		Mockito.when(goalRepository.deleteGoal(anyInt())).thenReturn(0);
		
		boolean result = goalService.deleteGoal(id);
		
		assertThat(result).isFalse();

		verify(goalRepository, times(1)).deleteGoal(anyInt());
	}
	
	@Test
	@DisplayName("findAllGoals() - Goals Found")
	public void testFindAllGoalsWithGoalsFound() throws Exception {
		List<Goal> mockGoals = new LinkedList<Goal>();
		mockGoals.add(new Goal(1,"Test Goal 1", "Test Desc", "example.com/image", LocalDate.of(2052, 1, 1), 100.0f, 0.0f));
		mockGoals.add(new Goal(2,"Test Goal 2", "Test Desc", "example.com/image", LocalDate.of(2052, 1, 1), 100.0f, 0.0f));
		
		Mockito.when(goalRepository.findAll()).thenReturn(mockGoals);
		
		List<Goal> foundGoals = goalService.findAllGoals();
		
		assertThat(foundGoals).usingRecursiveComparison().isEqualTo(mockGoals);
		
		verify(goalRepository, times(1)).findAll();
	}
	
	@Test
	@DisplayName("findAllGoals() - Goals Not Found")
	public void testFindAllGoalsWithGoalsNotFound() throws Exception {
		List<Goal> mockGoals = new LinkedList<Goal>();
		
		Mockito.when(goalRepository.findAll()).thenReturn(mockGoals);
		
		assertThrows(GoalNotFoundException.class, () -> goalService.findAllGoals());
		
		verify(goalRepository, times(1)).findAll();
	}
}
