package com.market.controller;

import java.util.HashSet;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.market.entity.Product;
import com.market.entity.User;
import com.market.service.ProductService;
import com.market.service.UserServiceImpl;

@Controller
public class HomeController {

	private final Logger log=LoggerFactory.getLogger(this.getClass());


	private final UserServiceImpl userService;
	

	private final ProductService productService;

	private final BCryptPasswordEncoder bCryptPasswordEncoder;

	User admin=null;
	
	boolean init=true;


	public HomeController(UserServiceImpl userService, ProductService productService, BCryptPasswordEncoder bCryptPasswordEncoder) {
		this.userService = userService;
		this.productService = productService;
		this.bCryptPasswordEncoder = bCryptPasswordEncoder;
	}

	@RequestMapping("/")
	public String index()
	{
		if(init) {
		initUsers();
		initProducts();}
		
		return "index";
	}
	
	

	
	@RequestMapping("/registration")
	public String registration(Model model)
	{
		
		model.addAttribute("user", new User());
		return "registration";
	}
	
	
	@PostMapping("/reg")
	public String reg(@ModelAttribute User user)
	{

		String encodedPassword = bCryptPasswordEncoder.encode(user.getPassword());
		user.setPassword(encodedPassword);
		userService.registerUser(user);
		
		return "auth/login";
		
	}
	

	
	public void initUsers()
	{
		User user1 = new User(), user2=new User();
		
		admin=new User("admin","admin","admin","admin");
		
		user1.setEmail("a");
		user1.setFirstName("a");
		user1.setLastName("a");
		user1.setPassword("a");
		
		user2.setEmail("b");
		user2.setFirstName("b");
		user2.setLastName("b");
		user2.setPassword("b");
		
		String encodedPassword1 = bCryptPasswordEncoder.encode(user1.getPassword());
		String encodedPassword2 = bCryptPasswordEncoder.encode(user2.getPassword());
		String encodedPasswordAdmin = bCryptPasswordEncoder.encode(admin.getPassword());
		
		user1.setPassword(encodedPassword1);
		user2.setPassword(encodedPassword2);
		admin.setPassword(encodedPasswordAdmin);
		
		userService.registerUser(user1);
		userService.registerUser(user2);
		userService.registerUser(admin);
		
	}
	
	public void initProducts(){
		
		Product product1=new Product("gtx 1050", 40000, 10, "/images/1050.jpg", admin);
		Product product2=new Product("gtx 1060", 60000, 12, "/images/1060.jpg", admin);
		
		productService.save(product1);
		productService.save(product2);
		
		Set<Product> products=new HashSet<>();
		
		products.add(product1);
		products.add(product2);
		
		admin.setProducts(products);
		
		init=false;
		
	}
	
	
	
}
