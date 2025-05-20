package com.featherworld.project.board.model.service;

import java.util.List;
import java.util.Map;

import com.featherworld.project.board.model.dto.Board;
import com.featherworld.project.board.model.dto.BoardType;

public interface BoardService {

	/** 회원 여부 확인
	 * @author Jiho
	 * @param memberNo 현재 조회 중인 회원 번호
	 */
	int checkMember(int memberNo);
	
	/** 현재 회원의 게시판 종류 번호(boardCode) 조회
	 * @author Jiho
	 * @param memberNo : 현재 조회 중인 회원 번호
	 */
	List<BoardType> selectBoardType(int memberNo);

	/** 현재 선택된 게시판의 삭제되지 않은 게시글 목록 조회 / pagination 객체 반환
	 * @author Jiho
	 * @param currentBoardCode
	 * @param cp
	 * @return {"boardList" : List<Board>, "pagination" : Pagination}
	 */
	Map<String, Object> selectBoardList(int currentBoardCode, int cp);

	/** 좋아요 체크 여부 서비스
	 * @author 허배령
	 * @param map
	 * @return
	 */
	int boardLike(Map<String, Integer> map);

	/** 게시글 상세 조회
	 * @author 허배령
	 * @param map
	 * @return
	 */
	Board selectOne(Map<String, Integer> map);



}
