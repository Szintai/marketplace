package com.market.repository;

import org.springframework.data.repository.CrudRepository;

import com.market.entity.Role;

public interface RoleRepository extends CrudRepository<Role, Long> {

	
	Role findByRole(String role);
	
	
	
}
