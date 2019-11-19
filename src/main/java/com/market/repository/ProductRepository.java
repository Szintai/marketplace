package com.market.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import com.market.entity.Product;

public interface ProductRepository extends CrudRepository<Product, Long> {

	List<Product> findAll();
	
	Optional<Product> findById(Long id);
	
	
}
