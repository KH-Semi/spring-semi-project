package com.featherworld.project.miniHome.model.service;

import java.util.List;

import com.featherworld.project.board.model.dto.Comment;

public interface MiniCommentService {

	/** 댓글 목록 조회 서비스
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