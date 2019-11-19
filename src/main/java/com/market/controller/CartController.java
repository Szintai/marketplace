package com.market.controller;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.market.entity.Item;
import com.market.entity.Product;



@Controller
public class CartController {

	
	private final Logger log = LoggerFactory.getLogger(this.getClass());
	
	List<Item> cart=null;
	
	@RequestMapping("/cart")
	public String cart(Model model,HttpSession session)
	{
	    	
		if(session.getAttribute("cart") != null)
		{
			 cart=(List<Item>) session.getAttribute("cart");
			log.info("idaigeljutottunk");
		model.addAttribute("cart", cart);
		}
			
		return "cart";
	}
	
	
	@PostMapping("/cartDeleteItem")
	public String cartDeleteItem(@ModelAttribute Item item, HttpSession session)
	{
	
		Iterator<Item> iterator=cart.iterator();
		
		while(iterator.hasNext())
		{
			Item i=iterator.next();
			if(i.getProduct().getId() == item.getProduct().getId())
			{iterator.remove();}
			
			
		}
		
		
	//  session.setAttribute("cart", cart);
	  
		log.info(cart.size()+"");
			
		return "redirect:/cart";
	}
	
	
}
