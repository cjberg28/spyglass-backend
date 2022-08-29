package com.skillstorm.spyglass.repositories;

import java.time.LocalDate;

import javax.validation.Valid;
import javax.validation.constraints.Email;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.skillstorm.spyglass.models.User;

@Repository
public interface UserRepository extends CrudRepository<User,String> {

	@Modifying
	@Query("update User u set u.firstName = ?2, u.lastName = ?3, u.dateOfBirth = ?4, u.password = ?5 where u.email = ?1")
	int updateUser(String email, String firstName, String lastName, LocalDate dateOfBirth, String password);

	@Modifying
	@Query("delete User u where u.email = ?1")
	int deleteUser(@Valid @Email String email);
	
}
