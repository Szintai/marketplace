package com.market.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name= "comments")
public class Comment {
	
	@Id @GeneratedValue
	private Long id;
	
	private String text;
	
	@ManyToOne
    @JoinColumn(name = "product_id")
	private Product product;
	
	@ManyToOne
    @JoinColumn(name = "user_id")
	private User user;
	
	

	public Comment() {
	}
	
	

	public Comment(String text, Product product, User user) {
		this.text = text;
		this.product = product;
		this.user = user;
	}



	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getText() {
		return text;
	}



	public void setText(String text) {
		this.text = text;
	}



	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
	
	
	

}
