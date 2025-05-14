package com.featherworld.project.board.model.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.featherworld.project.board.model.dto.Board;
import com.featherworld.project.board.model.dto.BoardType;
import com.featherworld.project.board.model.mapper.BoardMapper;

@Service
@Transactional(rollbackFor = Exception.class)
public class BoardServiceImpl implements BoardService {

	@Autowired
	BoardMapper mapper;
	
	// 회원 여부 확인
	@Override
	public int checkMember(int memberNo) {
		return mapper.checkMember(memberNo);
	}
	
	// 현재 회원의 게시판 종류 번호(boardCode) 조회
	@Override
	public List<BoardType> selectBoardType(int memberNo) {
		return mapper.selectBoardType(memberNo);
	}
	
	// 해당 게시판에서 삭제되지 않은 게시글 조회
	@Override
	public List<Board> selectBoardList(int currentBoardCode) {
		return mapper.selectBoardList(currentBoardCode);
	}
}
