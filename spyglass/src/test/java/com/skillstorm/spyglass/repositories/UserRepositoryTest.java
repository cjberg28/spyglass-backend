package com.skillstorm.spyglass.repositories;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import com.skillstorm.spyglass.models.User;

@SpringBootTest
@ActiveProfiles("dev")
public class UserRepositoryTest {
	@Autowired
	UserRepository userRepository;
	
//	@Test
//	@DisplayName("updateUser()")
//	public void testUpdateUser() throws Exception {
//		User user = new User("jchan@aol.com", "Jackie", "Chan", LocalDate.of(1954, 4, 7));
//		
//		int result = userRepository.updateUser(user.getEmail(), user.getFirstName(), user.getLastName(), user.getDateOfBirth());
//		
//		assertThat(result).isEqualTo(1);
//	}
	
//	@Test
//	@DisplayName("deleteUser()")
//	public void testDeleteUser() throws Exception {
//		User user = new User("jstatham@ymail.com", "Jason", "Statham", LocalDate.of(1967, 7, 26));
//		
//		int result = userRepository.deleteUser(user.getEmail());
//		
//		assertThat(result).isEqualTo(1);
//	}
}
