package com.featherworld.project.board.model.service;

import java.util.List;
import java.util.Map;

import com.featherworld.project.board.model.dto.Board;

public interface BoardService {
	
	/** 폴더 목록 조회
	 * @author 허배
	 * @param memberNo
	 * @return
	 */
	List<Board> selectBoardTypeList(int memberNo);

    /** 폴더 추가
     * @author 허배
     * @param board
     * @return
     */
    int addBoardType(Board board);

    /** 폴더 수정
     * @author 허배
     * @param board
     * @return
     */
    int editBoardType(Board board);

    /** 폴더 삭제
     * @author 허배
     * @param boardCode
     * @return
     */
    int removeBoardType(int boardCode);

    /** 일촌공개 접근 가능 여부
     * @author 허배
     * @param loginMemberNo
     * @param ownerNo
     * @return
     */
    boolean canAccessFriendBoard(int loginMemberNo, int ownerNo);

	/** 좋아요 체크 여부
	 * @author 허배
	 * @param map
	 * @return
	 */
	int boardLike(Map<String, Integer> map);

}
