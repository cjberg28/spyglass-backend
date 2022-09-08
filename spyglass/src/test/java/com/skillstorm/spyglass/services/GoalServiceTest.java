package com.skillstorm.spyglass.services;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.time.LocalDate;
import java.util.Optional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;

import com.skillstorm.spyglass.models.Goal;
import com.skillstorm.spyglass.repositories.GoalRepository;

@SpringBootTest
public class GoalServiceTest {
	
	@InjectMocks //Injects fake dependencies - This is what you should be testing in this file
	GoalServiceImplementation goalService;
	
	@Mock //Injects an entire fake object
	GoalRepository goalRepository;
	
	@Test
	@DisplayName("findById() - Goal Found")
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
}
