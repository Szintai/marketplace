package com.market.repository;



import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import com.market.entity.User;

public interface UserRepository extends CrudRepository<User, Long> {
	
	User findByEmail(String email);
	
	Optional<User> findById(Long id);

}
