package com.featherworld.project.miniHome.model.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.featherworld.project.board.model.dto.Comment;

@Mapper
public interface CommentMapper {

	/** 댓글 목록 조회
	 * @param boardNo
	 * @return
	 */
	List<Comment> select(int boardNo);


	/** 댓글 삭제
	 * @param commentNo
	 * @return
	 */
	int delete(int commentNo);
	
	
	

}