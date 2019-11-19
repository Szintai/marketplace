package com.market.service;

import com.market.entity.User;

public interface UserService {
	
	public String registerUser(User user);
	
	public User findByEmail(String email);
	
	

}
