package com.featherworld.project.miniHome.model.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.featherworld.project.board.model.dto.Comment;
import com.featherworld.project.miniHome.model.mapper.CommentMapper;

@Service
@Transactional(rollbackFor = Exception.class)
public class MiniCommentServiceImpl implements MiniCommentService{

	@Autowired
	private CommentMapper mapper;
	
	// 댓글 목록 조회 서비스
	@Override
	public List<Comment> select(int boardNo) {
		return mapper.select(boardNo);
	}
	
	
	// 댓글 삭제
	@Override
	public int delete(int commentNo) {
		return mapper.delete(commentNo);
	}
	
	
	
	
}