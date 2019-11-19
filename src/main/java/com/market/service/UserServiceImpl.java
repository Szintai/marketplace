package com.market.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.market.entity.Role;
import com.market.entity.User;
import com.market.repository.RoleRepository;
import com.market.repository.UserRepository;

@Service
public class UserServiceImpl implements UserService, UserDetailsService {

	
	private final Logger log = LoggerFactory.getLogger(this.getClass());

	private UserRepository userRepository;

	private RoleRepository roleRepository;

	private final String USER_ROLE = "USER";

	@Autowired
	public UserServiceImpl(UserRepository userRepository, RoleRepository roleRepository) {
		this.userRepository = userRepository;
		this.roleRepository = roleRepository;
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = findByEmail(username);
		if (user == null) {
			throw new UsernameNotFoundException(username);
		}

		return new UserDetailsImpl(user);
	}

	@Override
	public User findByEmail(String email) {
		return userRepository.findByEmail(email);
	}
	
	
	public User findById(Long id) {
		
		return userRepository.findById(id).orElse(new User());
	}
	

	@Override
	public String registerUser(User userToRegister) {
		User userCheck = userRepository.findByEmail(userToRegister.getEmail());

		if (userCheck != null)
			return "alreadyExists";

		Role userRole = roleRepository.findByRole(USER_ROLE);
		if (userRole != null) {
			log.info("új role");
			userToRegister.getRoles().add(userRole);
			
		} else {
			log.info("létező role");
			userToRegister.addRoles(USER_ROLE);
		}
		
		userToRegister.setEnabled(true);
	
		userRepository.save(userToRegister);
		

		return "ok";
	}

	

	public void save(User user)
	{
		userRepository.save(user);
	}
	
	
}
