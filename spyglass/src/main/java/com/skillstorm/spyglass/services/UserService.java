package com.skillstorm.spyglass.services;

import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.Email;

import com.skillstorm.spyglass.models.User;

public interface UserService {

	User findByEmail(@Valid @Email String email);

	List<User> findAllUsers();

	User createUser(User user);

	boolean updateUser(User user);

	boolean deleteUser(@Valid @Email String email);

}
