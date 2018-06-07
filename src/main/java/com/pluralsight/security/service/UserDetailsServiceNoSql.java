package com.pluralsight.security.service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import com.pluralsight.security.entity.User;
import com.pluralsight.security.repository.UserRepository;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class UserDetailsServiceNoSql implements UserDetailsService {

	private final UserRepository userRepository;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = userRepository.findByUsername(username);
		if (user == null) {
			throw new UsernameNotFoundException(username);
		}
		return org.springframework.security.core.userdetails.User.withUsername(user.getUsername()).password("{noop}"+user.getPassword()).roles("USER").build();
		//return org.springframework.security.core.userdetails.User.builder().username(username).password("{noop}"+user.getPassword()).roles("USER").build();	
	}

}