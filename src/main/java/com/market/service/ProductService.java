package com.market.service;

import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.market.entity.Product;
import com.market.repository.ProductRepository;

@Service
public class ProductService {
	
	private final ProductRepository productRepository;


	public ProductService(ProductRepository productRepository) {
		
		this.productRepository = productRepository;
	}
	
	
	public List<Product> findAll(){
		
		return productRepository.findAll();
	}
	
	public Product findById(Long id)
	{
		return productRepository.findById(id).orElse(new Product());
	}
	
	public void save(Product productToUpload)
	{
		
		productRepository.save(productToUpload);
		
	}
	
	
}
