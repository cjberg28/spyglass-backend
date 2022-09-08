package com.skillstorm.spyglass.services;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
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

import com.skillstorm.spyglass.exceptions.UserNotFoundException;
import com.skillstorm.spyglass.models.Goal;
import com.skillstorm.spyglass.models.User;
import com.skillstorm.spyglass.repositories.UserRepository;

@SpringBootTest
public class UserServiceTest {
	@InjectMocks //Injects fake dependencies - This is what you should be testing in this file
	UserServiceImplementation userService;
	
	@Mock //Injects an entire fake object
	UserRepository userRepository;
	
	@Test
	@DisplayName("findByEmail() - User Found")
	public void testFindByEmailWithUserFound() throws Exception {
		User mockUser = new User("jtester@skillstorm.com", "Jonathan", "Tester", LocalDate.of(2000,1,1));
		
		Mockito.when(userRepository.findById(any())).thenReturn(Optional.of(mockUser));
		
		User foundUser = userService.findByEmail(mockUser.getEmail());
		
		assertThat(foundUser).usingRecursiveComparison().isEqualTo(mockUser);
		
		verify(userRepository, times(1)).findById(any());
	}
	
	@Test
	@DisplayName("findByEmail() - User Not Found")
	public void testFindByEmailWithUserNotFound() throws Exception {
		User mockUser = new User("jtester@skillstorm.com", "Jonathan", "Tester", LocalDate.of(2000,1,1));
		
		Mockito.when(userRepository.findById(any())).thenReturn(Optional.empty());
		
		assertThrows(UserNotFoundException.class, () -> {User foundUser = userService.findByEmail(mockUser.getEmail());});
		
		verify(userRepository, times(1)).findById(any());
	}
	
	@Test
	@DisplayName("findAllUsers() - Users Found")
	public void testFindAllUsersWithUsersFound() throws Exception {
		List<User> mockUsers = new LinkedList<User>();
		mockUsers.add(new User("jtester@skillstorm.com", "Jonathan", "Tester", LocalDate.of(2000,1,1)));
		mockUsers.add(new User("jtester2@skillstorm.com", "Jonathan", "Tester II", LocalDate.of(2000,1,2)));
		
		Mockito.when(userRepository.findAll()).thenReturn(mockUsers);
		
		List<User> foundUsers = userService.findAllUsers();
		
		assertThat(foundUsers).usingRecursiveComparison().isEqualTo(mockUsers);
		
		verify(userRepository, times(1)).findAll();
	}
	
	@Test
	@DisplayName("findAllUsers() - Users Not Found")
	public void testFindAllUsersWithUsersNotFound() throws Exception {
		List<User> mockUsers = new LinkedList<User>();
		
		Mockito.when(userRepository.findAll()).thenReturn(mockUsers);
		
		assertThrows(UserNotFoundException.class, () -> {List<User> foundUsers = userService.findAllUsers();});
		
		verify(userRepository, times(1)).findAll();
	}
	
	@Test
	@DisplayName("createUser()")
	public void testCreateUser() throws Exception {
		User mockUser = new User("jtester@skillstorm.com", "Jonathan", "Tester", LocalDate.of(2000,1,1));
		
		//No auto-generated IDs.
		Mockito.when(userRepository.save(any())).thenReturn(mockUser);
		
		User createdUser = userService.createUser(mockUser);
		
		assertThat(createdUser).usingRecursiveComparison().isEqualTo(mockUser);

		verify(userRepository, times(1)).save(any());
	}
	
	@Test
	@DisplayName("updateUser() - User Found")
	public void testUpdateUserWithUserFound() throws Exception {
		User mockUser = new User("jtester@skillstorm.com", "Jonathan", "Tester", LocalDate.of(2000,1,1));
		
		//1 - Update successful
		Mockito.when(userRepository.updateUser(any(),any(),any(),any())).thenReturn(1);
		
		boolean result = userService.updateUser(mockUser);
		
		assertThat(result).isTrue();

		verify(userRepository, times(1)).updateUser(any(),any(),any(),any());
	}
	
	@Test
	@DisplayName("updateUser() - User Not Found")
	public void testUpdateUserWithUserNotFound() throws Exception {
		User mockUser = new User("jtester@skillstorm.com", "Jonathan", "Tester", LocalDate.of(2000,1,1));
		
		//0 - Update failed
		Mockito.when(userRepository.updateUser(any(),any(),any(),any())).thenReturn(0);
		
		boolean result = userService.updateUser(mockUser);
		
		assertThat(result).isFalse();

		verify(userRepository, times(1)).updateUser(any(),any(),any(),any());
	}
	
	@Test
	@DisplayName("deleteUser() - User Found")
	public void testDeleteUserWithUserFound() throws Exception {
		User mockUser = new User("jtester@skillstorm.com", "Jonathan", "Tester", LocalDate.of(2000,1,1));
		
		//1 - Delete successful
		Mockito.when(userRepository.deleteUser(any())).thenReturn(1);
		
		boolean result = userService.deleteUser(mockUser.getEmail());
		
		assertThat(result).isTrue();

		verify(userRepository, times(1)).deleteUser(any());
	}
	
	@Test
	@DisplayName("deleteUser() - User Not Found")
	public void testDeleteUserWithUserNotFound() throws Exception {
		User mockUser = new User("jtester@skillstorm.com", "Jonathan", "Tester", LocalDate.of(2000,1,1));
		
		//1 - Delete successful
		Mockito.when(userRepository.deleteUser(any())).thenReturn(0);
		
		boolean result = userService.deleteUser(mockUser.getEmail());
		
		assertThat(result).isFalse();

		verify(userRepository, times(1)).deleteUser(any());
	}
}
