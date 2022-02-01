package com.cvdam.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cvdam.model.User;

public interface UserRepository extends JpaRepository<User, Long> {
	
	Optional<User> findByEmail(String email);
}
