package com.skillstorm.spyglass.controllers;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDate;
import java.util.LinkedList;
import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.skillstorm.spyglass.exceptions.GoalNotFoundException;
import com.skillstorm.spyglass.models.Goal;
import com.skillstorm.spyglass.models.User;
import com.skillstorm.spyglass.services.GoalServiceImplementation;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("dev")
public class GoalControllerTest {
	
	@Autowired
	GoalController goalController;
	
	@Value("${authUsername}")
	String username;
	
	@Value("${authPassword")
	String password;
	
	@Autowired
	private MockMvc mockMvc;
	
	@Autowired
	private ObjectMapper mapper;
	
	@MockBean
	private GoalServiceImplementation goalService;
	
	@Test
	@WithMockUser //No need to enter in username or password, assumed to be correct here.
	@DisplayName("findAllGoals() - Valid Credentials")
	public void testFindAllGoalsWithValidCredentials() throws Exception {
		//1. Make a list of mock data to be returned from your fake service call.
		List<Goal> mockGoals = new LinkedList<Goal>();
		mockGoals.add(new Goal(1,"Test Goal 1", "Test Desc", "example.com/image", LocalDate.of(2052, 1, 1), 100.0f, 0.0f));
		mockGoals.add(new Goal(2,"Test Goal 2", "Test Desc", "example.com/image", LocalDate.of(2052, 1, 2), 100.0f, 0.0f));
		
		//2. Set up a stub to not actually call the service method, and return the value instead.
		Mockito.when(goalService.findAllGoals()).thenReturn(mockGoals);
		
		//3. Perform the test, expecting the mock data to be returned from the fake service call.
		mockMvc.perform(MockMvcRequestBuilders.get("/goals"))
			   .andExpect(status().isOk())
			   .andExpect(content().string(mapper.writeValueAsString(mockGoals)));
		
		//4. Verify that the service method was "called" once.
		verify(goalService, times(1)).findAllGoals();
	}
	
	@Test
	@DisplayName("findAllGoals() - Invalid Credentials")
	public void testFindAllGoalsWithInvalidCredentials() throws Exception {
		List<Goal> mockGoals = new LinkedList<Goal>();
		mockGoals.add(new Goal(1,"Test Goal 1", "Test Desc", "example.com/image", LocalDate.of(2052, 1, 1), 100.0f, 0.0f));
		mockGoals.add(new Goal(2,"Test Goal 2", "Test Desc", "example.com/image", LocalDate.of(2052, 1, 2), 100.0f, 0.0f));
		Mockito.when(goalService.findAllGoals()).thenReturn(mockGoals);
		
		mockMvc.perform(MockMvcRequestBuilders.get("/goals").with(SecurityMockMvcRequestPostProcessors.httpBasic(username, "nice")))
			   .andExpect(MockMvcResultMatchers.status().isUnauthorized());
		verify(goalService, times(0)).findAllGoals();
	}
	
	@Test
	@WithMockUser
	@DisplayName("findByUser() - Valid Credentials, User Found")
	public void testFindByUserWithValidCredentialsAndUserFound() throws Exception {
		List<Goal> mockGoals = new LinkedList<Goal>();
		mockGoals.add(new Goal(1,"Test Goal 1", "Test Desc", "example.com/image", LocalDate.of(2052, 1, 1), 100.0f, 0.0f));
		mockGoals.add(new Goal(2,"Test Goal 2", "Test Desc", "example.com/image", LocalDate.of(2052, 1, 2), 100.0f, 0.0f));
		
		Mockito.when(goalService.findByUser(any())).thenReturn(mockGoals);
		
		mockMvc.perform(MockMvcRequestBuilders.get("/goals/user/jtester@skillstorm.com"))
			   .andExpect(status().isOk())
			   .andExpect(content().string(mapper.writeValueAsString(mockGoals)));
		
		verify(goalService, times(1)).findByUser(any());
	}
	
	@Test
	@WithMockUser
	@DisplayName("findByUser() - Valid Credentials, User Not Found")
	public void testFindByUserWithValidCredentialsAndUserNotFound() throws Exception {
		
		assertThrows(GoalNotFoundException.class, () -> {
			Mockito.when(goalService.findByUser(any())).thenThrow(new GoalNotFoundException());
			
			mockMvc.perform(MockMvcRequestBuilders.get("/goals/user/jtester@skillstorm.com"));
			
			verify(goalService, times(1)).findByUser(any());
		});
		
//		Mockito.when(goalService.findByUser(any())).thenThrow(new GoalNotFoundException());
//		
//		mockMvc.perform(MockMvcRequestBuilders.get("/goals/user/jtester@skillstorm.com"));
		
//		verify(goalService, times(1)).findByUser(any());
	}
	
	@Test
	@DisplayName("findByUser() - Invalid Credentials, User Found")
	public void testFindByUserWithInvalidCredentialsAndUserFound() throws Exception {
		List<Goal> mockGoals = new LinkedList<Goal>();
		mockGoals.add(new Goal(1,"Test Goal 1", "Test Desc", "example.com/image", LocalDate.of(2052, 1, 1), 100.0f, 0.0f));
		mockGoals.add(new Goal(2,"Test Goal 2", "Test Desc", "example.com/image", LocalDate.of(2052, 1, 2), 100.0f, 0.0f));
		
		Mockito.when(goalService.findByUser(any())).thenReturn(mockGoals);
		
		mockMvc.perform(MockMvcRequestBuilders.get("/goals/user/jtester@skillstorm.com"))
			   .andExpect(status().isUnauthorized());
		
		verify(goalService, times(0)).findByUser(any());
	}
	
	@Test
	@DisplayName("findByUser() - Invalid Credentials, User Not Found")
	public void testFindByUserWithInvalidCredentialsAndUserNotFound() throws Exception {		
		Mockito.when(goalService.findByUser(any())).thenThrow(new GoalNotFoundException());
		
		mockMvc.perform(MockMvcRequestBuilders.get("/goals/user/jtester@skillstorm.com"))
			   .andExpect(status().isUnauthorized());
		
		verify(goalService, times(0)).findByUser(any());
	}
	
	@Test
	@WithMockUser
	@DisplayName("findById() - Valid Credentials, Goal Found")
	public void testFindByIdWithValidCredentialsAndGoalFound() throws Exception {
		int id = 1;
		Goal mockGoal = new Goal(1,"Test Goal 1", "Test Desc", "example.com/image", LocalDate.of(2052, 1, 1), 100.0f, 0.0f);
		
		//Don't use any() when service methods are expecting primitives, it gives NullPointerExceptions.
		Mockito.when(goalService.findById(anyInt())).thenReturn(mockGoal);
		
		mockMvc.perform(MockMvcRequestBuilders.get("/goals/" + id))
			   .andExpect(status().isOk())
			   .andExpect(content().string(mapper.writeValueAsString(mockGoal)));
		
		verify(goalService, times(1)).findById(anyInt());
	}
	
	@Test
	@WithMockUser
	@DisplayName("findById() - Valid Credentials, Goal Not Found")
	public void testFindByIdWithValidCredentialsAndGoalNotFound() throws Exception {
		assertThrows(GoalNotFoundException.class, () -> {
			int id = 1;
			
			//Don't use any() when service methods are expecting primitives, it gives NullPointerExceptions.
			Mockito.when(goalService.findById(anyInt())).thenThrow(new GoalNotFoundException());
			
			mockMvc.perform(MockMvcRequestBuilders.get("/goals/" + id));
			
			verify(goalService, times(1)).findById(anyInt());
		});
	}
	
	@Test
	@DisplayName("findById() - Invalid Credentials, Goal Found")
	public void testFindByIdWithInvalidCredentialsAndGoalFound() throws Exception {
		int id = 1;
		Goal mockGoal = new Goal(1,"Test Goal 1", "Test Desc", "example.com/image", LocalDate.of(2052, 1, 1), 100.0f, 0.0f);
		
		//Don't use any() when service methods are expecting primitives, it gives NullPointerExceptions.
		Mockito.when(goalService.findById(anyInt())).thenReturn(mockGoal);
		
		mockMvc.perform(MockMvcRequestBuilders.get("/goals/" + id))
			   .andExpect(status().isUnauthorized());
		
		verify(goalService, times(0)).findById(anyInt());
	}
	
	@Test
	@DisplayName("findById() - Invalid Credentials, Goal Not Found")
	public void testFindByIdWithInvalidCredentialsAndGoalNotFound() throws Exception {
		int id = 1;
		
		//Don't use any() when service methods are expecting primitives, it gives NullPointerExceptions.
		Mockito.when(goalService.findById(anyInt())).thenThrow(new GoalNotFoundException());
		
		mockMvc.perform(MockMvcRequestBuilders.get("/goals/" + id))
			   .andExpect(status().isUnauthorized());
		
		verify(goalService, times(0)).findById(anyInt());
	}
	
	@Test
	@WithMockUser
	@DisplayName("createGoal() - Valid Credentials")
	public void testCreateGoalWithValidCredentials() throws Exception {
		Goal oldGoal = new Goal(0,"Test Goal 1", "Test Desc", "example.com/image", LocalDate.of(2052, 1, 1), 100.0f, 0.0f);
		Goal newGoal = new Goal(1,"Test Goal 1", "Test Desc", "example.com/image", LocalDate.of(2052, 1, 1), 100.0f, 0.0f);
		
		Mockito.when(goalService.createGoal(any())).thenReturn(newGoal);
		
		mockMvc.perform(post("/goals").content(mapper.writeValueAsBytes(oldGoal)).contentType("application/json"))
			   .andExpect(status().isCreated())
			   .andExpect(content().string(mapper.writeValueAsString(newGoal)));
		
		verify(goalService, times(1)).createGoal(any());
	}
	
	@Test
	@DisplayName("createGoal() - Invalid Credentials")
	public void testCreateGoalWithInvalidCredentials() throws Exception {
		Goal oldGoal = new Goal(0,"Test Goal 1", "Test Desc", "example.com/image", LocalDate.of(2052, 1, 1), 100.0f, 0.0f);
		Goal newGoal = new Goal(1,"Test Goal 1", "Test Desc", "example.com/image", LocalDate.of(2052, 1, 1), 100.0f, 0.0f);
		
		Mockito.when(goalService.createGoal(any())).thenReturn(newGoal);
		
		mockMvc.perform(post("/goals").content(mapper.writeValueAsBytes(oldGoal)).contentType("application/json"))
			   .andExpect(status().isUnauthorized());
		
		verify(goalService, times(0)).createGoal(any());
	}
	
	@Test
	@WithMockUser
	@DisplayName("updateGoal() - Valid Credentials, Goal Found")
	public void testUpdateGoalWithValidCredentialsAndGoalFound() throws Exception {
		Goal mockGoal = new Goal(1,"Test Goal 1", "Test Desc", "example.com/image", LocalDate.of(2052, 1, 1), 100.0f, 0.0f);
		
		Mockito.when(goalService.updateGoal(any())).thenReturn(true);
		
		mockMvc.perform(put("/goals").content(mapper.writeValueAsBytes(mockGoal)).contentType("application/json"))
			   .andExpect(status().isOk())
			   .andExpect(content().string(mapper.writeValueAsString(true)));
		
		verify(goalService, times(1)).updateGoal(any());
	}
	
	@Test
	@WithMockUser
	@DisplayName("updateGoal() - Valid Credentials, Goal Not Found")
	public void testUpdateGoalWithValidCredentialsAndGoalNotFound() throws Exception {
		Goal mockGoal = new Goal(1,"Test Goal 1", "Test Desc", "example.com/image", LocalDate.of(2052, 1, 1), 100.0f, 0.0f);
		
		Mockito.when(goalService.updateGoal(any())).thenReturn(false);
		
		mockMvc.perform(put("/goals").content(mapper.writeValueAsBytes(mockGoal)).contentType("application/json"))
			   .andExpect(status().isBadRequest())
			   .andExpect(content().string(mapper.writeValueAsString(false)));
		
		verify(goalService, times(1)).updateGoal(any());
	}
	
	@Test
	@DisplayName("updateGoal() - Invalid Credentials, Goal Found")
	public void testUpdateGoalWithInvalidCredentialsAndGoalFound() throws Exception {
		Goal mockGoal = new Goal(1,"Test Goal 1", "Test Desc", "example.com/image", LocalDate.of(2052, 1, 1), 100.0f, 0.0f);
		
		Mockito.when(goalService.updateGoal(any())).thenReturn(true);
		
		mockMvc.perform(put("/goals").content(mapper.writeValueAsBytes(mockGoal)).contentType("application/json"))
			   .andExpect(status().isUnauthorized());
		
		verify(goalService, times(0)).updateGoal(any());
	}
	
	@Test
	@DisplayName("updateGoal() - Invalid Credentials, Goal Not Found")
	public void testUpdateGoalWithInvalidCredentialsAndGoalNotFound() throws Exception {
		Goal mockGoal = new Goal(1,"Test Goal 1", "Test Desc", "example.com/image", LocalDate.of(2052, 1, 1), 100.0f, 0.0f);
		
		Mockito.when(goalService.updateGoal(any())).thenReturn(false);
		
		mockMvc.perform(put("/goals").content(mapper.writeValueAsBytes(mockGoal)).contentType("application/json"))
			   .andExpect(status().isUnauthorized());
		
		verify(goalService, times(0)).updateGoal(any());
	}
	
	@Test
	@WithMockUser
	@DisplayName("deleteGoal() - Valid Credentials, Goal Found")
	public void testDeleteGoalWithValidCredentialsAndGoalFound() throws Exception {
		int id = 1;
		
		Mockito.when(goalService.deleteGoal(anyInt())).thenReturn(true);
		
		mockMvc.perform(delete("/goals/" + id))
			   .andExpect(status().isOk())
			   .andExpect(content().string(mapper.writeValueAsString(true)));
		
		verify(goalService, times(1)).deleteGoal(anyInt());
	}
	
	@Test
	@WithMockUser
	@DisplayName("deleteGoal() - Valid Credentials, Goal Not Found")
	public void testDeleteGoalWithValidCredentialsAndGoalNotFound() throws Exception {
		int id = 1;
		
		Mockito.when(goalService.deleteGoal(anyInt())).thenReturn(false);
		
		mockMvc.perform(delete("/goals/" + id))
			   .andExpect(status().isBadRequest())
			   .andExpect(content().string(mapper.writeValueAsString(false)));
		
		verify(goalService, times(1)).deleteGoal(anyInt());
	}
	
	@Test
	@DisplayName("deleteGoal() - Invalid Credentials, Goal Found")
	public void testDeleteGoalWithInvalidCredentialsAndGoalFound() throws Exception {
		int id = 1;
		
		Mockito.when(goalService.deleteGoal(anyInt())).thenReturn(true);
		
		mockMvc.perform(delete("/goals/" + id))
			   .andExpect(status().isUnauthorized());
		
		verify(goalService, times(0)).deleteGoal(anyInt());
	}
	
	@Test
	@DisplayName("deleteGoal() - Invalid Credentials, Goal Not Found")
	public void testDeleteGoalWithInvalidCredentialsAndGoalNotFound() throws Exception {
		int id = 1;
		
		Mockito.when(goalService.deleteGoal(anyInt())).thenReturn(false);
		
		mockMvc.perform(delete("/goals/" + id))
			   .andExpect(status().isUnauthorized());
		
		verify(goalService, times(0)).deleteGoal(anyInt());
	}
}
