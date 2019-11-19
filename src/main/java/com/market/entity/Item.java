package com.market.entity;

public class Item {
	
	
	Product product;
	
	int quantity;
	
	

	public Item(Product product, int quantity) {
		this.product = product;
		this.quantity = quantity;
	}

	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	
	public void incQuantity()
	{
		this.quantity++;
	}
	
	public void decQuantity()
	{
		this.quantity--;
	}
	
	

}
