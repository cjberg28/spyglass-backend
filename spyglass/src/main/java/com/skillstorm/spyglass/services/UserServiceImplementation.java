package com.skillstorm.spyglass.services;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;
import javax.validation.Valid;
import javax.validation.constraints.Email;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.skillstorm.spyglass.exceptions.UserNotFoundException;
import com.skillstorm.spyglass.models.User;
import com.skillstorm.spyglass.repositories.UserRepository;

@Service
@Transactional
public class UserServiceImplementation implements UserService {
	
	@Autowired
	private UserRepository userRepository;

	@Override
	public User findByEmail(@Valid @Email String email) {
		Optional<User> user = userRepository.findById(email);
		if (user.isPresent()) {
			return user.get();
		}
		throw new UserNotFoundException("The requested user was not found in the database.");
	}

	@Override
	public List<User> findAllUsers() {
		List<User> users = (List<User>) userRepository.findAll();
		if (!users.isEmpty()) {
			return users;
		}
		throw new UserNotFoundException("There are no users currently in the database.");
	}

	@Override
	public User createUser(User user) {
		return userRepository.save(user);
	}

	@Override
	public boolean updateUser(User user) {
		int result = userRepository.updateUser(user.getEmail(),user.getFirstName(),user.getLastName(),user.getDateOfBirth(),user.getPassword());
		if (result > 0) {
			return true;
		}
		return false;
	}

	@Override
	public boolean deleteUser(@Valid @Email String email) {
		int result = userRepository.deleteUser(email);
		if (result > 0) {
			return true;
		}
		return false;
	}

	
}
