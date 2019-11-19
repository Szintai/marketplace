package com.market.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.market.entity.Comment;
import com.market.repository.CommentRepository;

@Service
public class CommentService {

	private final CommentRepository commentRepository;
	
	@Autowired
	public CommentService(CommentRepository commentRepository) {
		this.commentRepository=commentRepository;
		
	}
	
	
	public void save(Comment comment)
	{
		commentRepository.save(comment);
		
	}
	
}
