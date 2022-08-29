package com.skillstorm.spyglass.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.skillstorm.spyglass.models.User;

@Repository
public interface UserRepository extends CrudRepository<User,String> {
	
}
