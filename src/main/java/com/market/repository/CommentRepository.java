package com.market.repository;

import org.springframework.data.repository.CrudRepository;

import com.market.entity.Comment;

public interface CommentRepository extends CrudRepository<Comment, Long> {

	
	
}
