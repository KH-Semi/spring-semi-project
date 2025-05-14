package com.featherworld.project.board.model.service;

import java.util.List;

import com.featherworld.project.board.model.dto.Board;
import com.featherworld.project.board.model.dto.BoardType;

public interface BoardService {

	/** 회원 여부 확인
	 * @author Jiho
	 * @param memberNo : 현재 조회 중인 회원 번호
	 * @return
	 */
	int checkMember(int memberNo);
	
	/** 현재 회원의 게시판 종류 번호(boardCode) 조회
	 * @author Jiho
	 * @param memberNo : 현재 조회 중인 회원 번호
	 * @return
	 */
	List<BoardType> selectBoardType(int memberNo);

	/** 해당 게시판의 게시글 조회
	 * @author Jiho
	 * @param currentBoardCode : 선택된 게시판 종류 번호
	 * @return
	 */
	List<Board> selectBoardList(int currentBoardCode);


}
