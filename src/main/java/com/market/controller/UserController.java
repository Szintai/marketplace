package com.market.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.market.entity.User;
import com.market.service.UserDetailsImpl;
import com.market.service.UserServiceImpl;

@Controller
public class UserController {

	
	private final Logger log=LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	UserServiceImpl userService;
	
	@RequestMapping("/profile")
	public String profile(Model model)
	{
		UserDetailsImpl userDetails=(UserDetailsImpl)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		
		User user=userDetails.getUser();
		
		model.addAttribute("user", user);
		model.addAttribute("products", user.getProducts());
		
		return "profile";
	}
	
	
	@RequestMapping("/user={id}")
	public String user(@PathVariable(value = "id") Long id, Model model) throws Exception {
		
		User user=userService.findById(id);
		
		model.addAttribute("user", user);
		model.addAttribute("products", user.getProducts());
		
		return "user";
	}
	
	
}
