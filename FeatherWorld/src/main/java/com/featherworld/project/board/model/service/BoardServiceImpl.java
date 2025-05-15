package com.featherworld.project.board.model.service;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.featherworld.project.board.model.dto.Board;
import com.featherworld.project.board.model.mapper.BoardMapper;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Transactional(rollbackFor = Exception.class)
@RequiredArgsConstructor
@Slf4j
public class BoardServiceImpl implements BoardService {
	
	private final BoardMapper mapper;
	
	/** 폴더 목록 조회
	 * @author 허배
	 * @param memberNo
	 * @return
	 */
    @Override
    public List<Board> selectBoardTypeList(int memberNo) {
        return mapper.selectBoardTypeList(memberNo);
    }

    /** 폴더 추가
     * @author 허배
     * @param board
     * @return
     */
    @Override
    public int addBoardType(Board board) {
        return mapper.insertBoardType(board);
    }

    /** 폴더 수정
     * @author 허배
     * @param board
     * @return
     */
    @Override
    public int editBoardType(Board board) {
        return mapper.updateBoardType(board);
    }

    /** 폴더 삭제
     * @author 허배
     * @param boardCode
     * @return
     */
    @Override
    public int removeBoardType(int boardCode) {
        return mapper.deleteBoardType(boardCode);
    }

    /** 일촌공개 접근 가능 여부
     * @author 허배
     * @param loginMemberNo
     * @param ownerNo
     * @return
     */
    @Override
    public boolean canAccessFriendBoard(int loginMemberNo, int ownerNo) {
        return mapper.checkFriendAccess(loginMemberNo, ownerNo) > 0;
    }

	// 게시글 좋아요 체크/해제
	@Override
	public int boardLike(Map<String, Integer> map) {
		
		int result = 0;
		
		// 1. 좋아요가 체크된 상태인 경우 (likeCheck == 1)
		// -> BOARD_LIKE 테이블에 DELETE 수행
		if(map.get("likeCheck") == 1) {
			
			result = mapper.deleteBoardLike(map);
			
			
		} else {
		// 2. 좋아요가 해제된 해제 경우 (likeCheck == 0)
		// -> BOARD_LIKE 테이블에 INSERT 수행
			result = mapper.insertBoardLike(map);
	    }
		
		// 3. 다시 해당 게시글의 좋아요 개수를 조회해서 반환
		if(result > 0) {
			return mapper.selectLikeCount(map.get("boardNo"));
		}
		
		return -1; // 좋아요 처리 실패
		
  }

}
