package com.skillstorm.spyglass.controllers;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
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
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.fasterxml.jackson.core.JsonProcessingException;
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
		List<User> mockUsers = new LinkedList<User>();
		mockUsers.add(new User("tester1@skillstorm.com","Tester","One",LocalDate.of(2000, 1, 28)));
		mockUsers.add(new User("tester2@skillstorm.com","Tester","Two",LocalDate.of(2000, 1, 1)));
		Mockito.when(userService.findAllUsers()).thenReturn(mockUsers);
		
		mockMvc.perform(MockMvcRequestBuilders.get("/users").with(SecurityMockMvcRequestPostProcessors.httpBasic(username, "nice")))
			   .andExpect(MockMvcResultMatchers.status().isUnauthorized());
		verify(userService, times(0)).findAllUsers();
	}
	
	@Test
	@WithMockUser
	@DisplayName("findByEmail() - Valid Credentials")
	public void testFindByEmailWithValidCredentials() throws Exception {
		User mockUser = new User("tester1@skillstorm.com","Tester","One",LocalDate.of(2000, 1, 28));
		
		Mockito.when(userService.findByEmail(any())).thenReturn(mockUser);
		
		mockMvc.perform(get("/users/tester1@skillstorm.com"))
			   .andExpect(status().isOk())
			   .andExpect(content().string(mapper.writeValueAsString(mockUser)));
		verify(userService, times(1)).findByEmail(any());
	}
	
	@Test
	@DisplayName("findByEmail() - Invalid Credentials")
	public void testFindByEmailWithInvalidCredentials() throws Exception {
		User mockUser = new User("tester1@skillstorm.com","Tester","One",LocalDate.of(2000, 1, 28));
		
		Mockito.when(userService.findByEmail(any())).thenReturn(mockUser);
		
		mockMvc.perform(MockMvcRequestBuilders.get("/users/tester1@skillstorm.com").with(SecurityMockMvcRequestPostProcessors.httpBasic(username, "nice")))
			   .andExpect(MockMvcResultMatchers.status().isUnauthorized());
		verify(userService, times(0)).findByEmail(any());
	}
	
	@Test
	@WithMockUser
	@DisplayName("createUser() - Valid Credentials")
	public void testCreateUserWithValidCredentials() throws Exception {
		User newUser = new User("tester1@skillstorm.com","Tester","One",LocalDate.of(2000, 1, 28));
		
		Mockito.when(userService.createUser(any())).thenReturn(newUser);
		
		mockMvc.perform(post("/users").content(mapper.writeValueAsString(newUser)).contentType("application/json"))
			   .andExpect(status().isCreated())
			   .andExpect(content().string(mapper.writeValueAsString(newUser)));
		
		verify(userService, times(1)).createUser(any());
	}
	
	@Test
	@DisplayName("createUser() - Invalid Credentials")
	public void testCreateUserWithInvalidCredentials() throws Exception {
		User newUser = new User("tester1@skillstorm.com","Tester","One",LocalDate.of(2000, 1, 28));
		
		Mockito.when(userService.createUser(any())).thenReturn(newUser);
		
		mockMvc.perform(post("/users").content(mapper.writeValueAsString(newUser)).contentType("application/json")
			   .with(SecurityMockMvcRequestPostProcessors.httpBasic(username, "nice")))
			   .andExpect(status().isUnauthorized());
		
		verify(userService, times(0)).createUser(any());
	}
	
	@Test
	@WithMockUser
	@DisplayName("updateUser() - Valid Credentials, User Found") //Service method returns true
	public void testUpdateUserWithValidCredentialsAndUserFound() throws Exception {
		User newUser = new User("tester1@skillstorm.com","Tester","One",LocalDate.of(2000, 1, 28));
		
		Mockito.when(userService.updateUser(any())).thenReturn(true);
		
		mockMvc.perform(put("/users").content(mapper.writeValueAsString(newUser)).contentType("application/json"))
			   .andExpect(status().isOk())
			   .andExpect(content().string(mapper.writeValueAsString(true)));
		
		verify(userService, times(1)).updateUser(any());
	}
	
	@Test
	@WithMockUser
	@DisplayName("updateUser() - Valid Credentials, User Not Found") //Service method returns false
	public void testUpdateUserWithValidCredentialsAndUserNotFound() throws Exception {
		User newUser = new User("tester1@skillstorm.com","Tester","One",LocalDate.of(2000, 1, 28));
		
		//Returning false from the service method triggers the controller method to give a bad request status code.
		Mockito.when(userService.updateUser(any())).thenReturn(false);
		
		mockMvc.perform(put("/users").content(mapper.writeValueAsString(newUser)).contentType("application/json"))
			   .andExpect(status().isBadRequest())
			   .andExpect(content().string(mapper.writeValueAsString(false)));
		
		verify(userService, times(1)).updateUser(any());
	}
	
	@Test
	@DisplayName("updateUser() - Invalid Credentials, User Found") //Service method returns true
	public void testUpdateUserWithInvalidCredentialsAndUserFound() throws Exception {
		User newUser = new User("tester1@skillstorm.com","Tester","One",LocalDate.of(2000, 1, 28));
		
		Mockito.when(userService.updateUser(any())).thenReturn(true);
		
		mockMvc.perform(put("/users").content(mapper.writeValueAsString(newUser)).contentType("application/json")
			   .with(SecurityMockMvcRequestPostProcessors.httpBasic(username, "nice")))
			   .andExpect(status().isUnauthorized());
		
		verify(userService, times(0)).updateUser(any());
	}
	
	@Test
	@DisplayName("updateUser() - Invalid Credentials, User Not Found") //Service method returns false
	public void testUpdateUserWithInvalidCredentialsAndUserNotFound() throws Exception {
		User newUser = new User("tester1@skillstorm.com","Tester","One",LocalDate.of(2000, 1, 28));
		
		Mockito.when(userService.updateUser(any())).thenReturn(false);
		
		mockMvc.perform(put("/users").content(mapper.writeValueAsString(newUser)).contentType("application/json")
			   .with(SecurityMockMvcRequestPostProcessors.httpBasic(username, "nice")))
			   .andExpect(status().isUnauthorized());
		
		verify(userService, times(0)).updateUser(any());
	}
	
	@Test
	@WithMockUser
	@DisplayName("deleteUser() - Valid Credentials, User Found")
	public void testDeleteUserWithValidCredentialsAndUserFound() throws Exception {
		String username = "jtester@skillstorm.com";
		Mockito.when(userService.deleteUser(any())).thenReturn(true);
		mockMvc.perform(delete("/users/" + username)).andExpect(status().isOk())
			   .andExpect(content().string(mapper.writeValueAsString(true)));
		verify(userService, times(1)).deleteUser(any());
	}
	
	@Test
	@WithMockUser
	@DisplayName("deleteUser() - Valid Credentials, User Not Found")
	public void testDeleteUserWithValidCredentialsAndUserNotFound() throws Exception {
		String username = "jtester@skillstorm.com";
		Mockito.when(userService.deleteUser(any())).thenReturn(false);
		mockMvc.perform(delete("/users/" + username)).andExpect(status().isBadRequest())
			   .andExpect(content().string(mapper.writeValueAsString(false)));
		verify(userService, times(1)).deleteUser(any());
	}
	
	@Test
	@DisplayName("deleteUser() - Invalid Credentials, User Found")
	public void testDeleteUserWithInvalidCredentialsAndUserFound() throws Exception {
		String username = "jtester@skillstorm.com";
		
		Mockito.when(userService.deleteUser(any())).thenReturn(true);
		
		mockMvc.perform(delete("/users")
			   .with(SecurityMockMvcRequestPostProcessors.httpBasic(username, "nice")))
			   .andExpect(status().isUnauthorized());
		
		verify(userService, times(0)).deleteUser(any());
	}
	
	@Test
	@DisplayName("deleteUser() - Invalid Credentials, User Not Found")
	public void testDeleteUserWithInvalidCredentialsAndUserNotFound() throws Exception {
		String username = "jtester@skillstorm.com";
		
		Mockito.when(userService.deleteUser(any())).thenReturn(false);
		
		mockMvc.perform(delete("/users")
			   .with(SecurityMockMvcRequestPostProcessors.httpBasic(username, "nice")))
			   .andExpect(status().isUnauthorized());
		
		verify(userService, times(0)).deleteUser(any());
	}
}
