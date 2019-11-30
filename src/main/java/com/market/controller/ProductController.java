package com.market.controller;


import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.market.entity.Comment;
import com.market.entity.Item;
import com.market.entity.Product;
import com.market.entity.User;
import com.market.repository.UserRepository;
import com.market.service.CommentService;
import com.market.service.ProductService;
import com.market.service.UserDetailsImpl;
import com.market.service.UserService;
import com.market.service.UserServiceImpl;

@Controller
public class ProductController {

	private final ProductService productService;
	

	private final CommentService commentService;
	

	private final UserServiceImpl userService;
	
	private List<Item> cart = null;
	
	private List<Product> products;
	
	private final Logger log = LoggerFactory.getLogger(this.getClass());
	
	public static String uploadDirectory = System.getProperty("user.dir")+"/src/main/resources/static/images/";

	public ProductController(ProductService productService, CommentService commentService, UserServiceImpl userService) {
		this.productService = productService;
		this.commentService = commentService;
		this.userService = userService;
	}

	@RequestMapping("/products")
	public String items(Model model)
	{
		products=productService.findAll();
		model.addAttribute("products",products);
		
		
		log.info(products.size() +"");
		return "products";
	}
	
	
	@PostMapping("/prod")
	public String addProductToCart(@ModelAttribute Product product , HttpSession session)
	{

		if(session.getAttribute("cart") == null)
		{
			
			cart= new ArrayList<Item>();
			
			session.setAttribute("cart", cart);
		}
		
		if(product.getId() != null )
		{
			if(!itemIsinTheCart(product.getId())) {
				cart.add(new Item(productService.findById(product.getId()),1));
			}
			
		}
		
		
		return "redirect:/products";
	}
	
	
	private boolean itemIsinTheCart(Long id)
	{

		for(Item item : cart)
		{
			if(item.getProduct().getId() == id) { item.incQuantity() ; return true;}
		}

		return false;
	}
	
	
   @RequestMapping("/productUpload")
   public String productUpload(Model model)
   {
	   model.addAttribute("product", new Product());
	   
	   return "productUpload";
   }
	
	
   @PostMapping("/prodUpload")
   public String prodUpload(@ModelAttribute Product product, @RequestParam("picture") MultipartFile picture)
   {
	   
	   product.setPicturePath("/images/" + product.getName()+".jpg");
	   product.setQuantity(1);
	   
	   UserDetailsImpl userDetails=(UserDetailsImpl)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		
	   User user=userDetails.getUser();
	   
	   product.setUser(user);
	   
	   productService.save(product);
	   
	   
	  
       try {
    	byte[] bytes = picture.getBytes();
        Path path = Paths.get(uploadDirectory + product.getName()+".jpg");
		Files.write(path, bytes);
	} catch (IOException e) {
	
		e.printStackTrace();
	}
	   
	   
	   return "redirect:/products";
   }
	
   
	@RequestMapping("/product={id}")
	public String user(@PathVariable(value = "id") Long id, Model model) throws Exception {
		
		Product product=productService.findById(id);
		
		model.addAttribute("product", product);
		
		model.addAttribute("comments", product.getComments());
		
		return "product";
	}
   
	@PostMapping("/comm")
	public String comm( @RequestParam(value = "comment", required = true) String text,
			@ModelAttribute Product product ) {
		

        UserDetailsImpl userDetails=(UserDetailsImpl)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		
		User user=userDetails.getUser();
		
		Product productToUpdate=productService.findById(product.getId());
		
        Comment comment=new Comment(text, productToUpdate , user);
		
		commentService.save(comment);
		
		
		Set<Comment> comments=null;
		
		if(user.getComments().size() > 0) {
			comments=user.getComments();}
		else
		{comments=new HashSet<>();}
		
		comments.add(comment);
		user.setComments(comments);
		
		userService.save(user); 
		
		comments=null;
		if(productToUpdate.getComments().size() >0) {
			comments=productToUpdate.getComments();
		}
		else {comments=new HashSet<>();}
			
		comments.add(comment);
		productToUpdate.setComments(comments);

		productService.save(productToUpdate);
			
		return "redirect:/product="+product.getId();
	}
	
   
}
