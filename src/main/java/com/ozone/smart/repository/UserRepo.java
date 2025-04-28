package com.ozone.smart.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.ozone.smart.entity.User;

@Repository
public interface UserRepo extends JpaRepository<User, String> {
	
	Optional<User>findByusername(String username);
	

	@Query("SELECT cd FROM User cd ORDER BY cd.username ASC")
	List<User> getListOfUsers();

}
