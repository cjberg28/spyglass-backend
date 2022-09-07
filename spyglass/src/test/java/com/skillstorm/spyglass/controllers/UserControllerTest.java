package com.skillstorm.spyglass.controllers;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
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
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.skillstorm.spyglass.models.User;
import com.skillstorm.spyglass.services.UserServiceImplementation;

import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("dev")
public class UserControllerTest {
	
	@Autowired
	UserController userController;
	
	@Value("${authUsername}")
	String username;
	
	@Value("${authPassword")
	String password;
	
	@Autowired
	private MockMvc mockMvc;
	
	@Autowired
	private ObjectMapper mapper;
	
	@MockBean
	private UserServiceImplementation userService;
	
	@Test
	@WithMockUser //No need to enter in username or password, assumed to be correct here.
	@DisplayName("findAllUsers() - Valid Credentials")
	public void testFindAllUsersWithValidCredentials() throws Exception {
		//1. Make a list of mock data to be returned from your fake service call.
		List<User> mockUsers = new LinkedList<User>();
		mockUsers.add(new User("tester1@skillstorm.com","Tester","One",LocalDate.of(2000, 1, 28)));
		mockUsers.add(new User("tester2@skillstorm.com","Tester","Two",LocalDate.of(2000, 1, 1)));
		
		//2. Set up a stub to not actually call the service method, and return the value instead.
		Mockito.when(userService.findAllUsers()).thenReturn(mockUsers);
		
		//3. Perform the test, expecting the mock data to be returned from the fake service call.
		mockMvc.perform(MockMvcRequestBuilders.get("/users"))
			   .andExpect(status().isOk())
			   .andExpect(content().string(mapper.writeValueAsString(mockUsers)));
		
		//4. Verify that the service method was "called" once.
		verify(userService, times(1)).findAllUsers();
	}
	
	@Test
	@DisplayName("findAllUsers() - Invalid Credentials")
	public void testFindAllUsersWithInvalidCredentials() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.get("/users").with(SecurityMockMvcRequestPostProcessors.httpBasic(username, "nice")))
			   .andExpect(MockMvcResultMatchers.status().isUnauthorized());
	}
	
}
