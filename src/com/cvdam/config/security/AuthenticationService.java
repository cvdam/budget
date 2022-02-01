package com.cvdam.config.security;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.cvdam.model.User;
import com.cvdam.repository.UserRepository;

//Service class to run authentication logic

@Service
public class AuthenticationService implements UserDetailsService {
	
	@Autowired
	private UserRepository userRepository;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		
		Optional<User> user = userRepository.findByEmail(username);
		if(user.isPresent()) {
			return user.get();
		}
		throw new UsernameNotFoundException("Invalid data");
	}

}
