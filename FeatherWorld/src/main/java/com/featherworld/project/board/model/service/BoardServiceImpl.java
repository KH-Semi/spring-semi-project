package com.featherworld.project.board.model.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.featherworld.project.board.model.dto.Board;
import com.featherworld.project.board.model.dto.BoardImg;
import com.featherworld.project.board.model.dto.BoardType;
import com.featherworld.project.board.model.dto.Comment;
import com.featherworld.project.board.model.mapper.BoardMapper;
import com.featherworld.project.common.dto.Pagination;

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
	
	// 현재 선택된 게시판의 삭제되지 않은 게시글 목록 조회/해당 pagination 객체 반환
	@Override
	public Map<String, Object> selectBoardList(int currentBoardCode, int cp) {
		
		// 0. 반환할 Map 인스턴스(객체) 생성
		Map<String, Object> map = new HashMap<>();
		
		// 1. 게시판 종류 번호로 해당 게시판의 게시글 목록 가져오기
		
		// 1-1. 해당 게시판의 삭제되지 않은 총 게시글 개수(listCount) 조회
		int listCount = mapper.getListCount(currentBoardCode);
		
		// 게시글이 존재하지 않는다면, 빈 map return
		if(listCount == 0) return map;
		
		// 1-2. 현재 페이지(cp), 총 게시글 개수(listCount)를 기준으로
		// 		pagination 객체 생성
		Pagination pagination = new Pagination(cp, listCount);
		
		// 1-3. 생성된 pagination 객체의 필드값(limit)을 기준으로
		// 		해당 페이지에 포함되는 게시글 목록만 가져옴
		//		RowBounds 객체(MyBatis 제공) 활용
		int limit = pagination.getLimit();
		int offset = (cp - 1) * limit;
		RowBounds rowBounds = new RowBounds(offset, limit);
		
		// RowBounds와 현재 게시판 종류 번호(currentBoardCode)를 매개변수로 지정
		// rowBounds 순서는 반드시 두 번째!
		List<Board> boardList = mapper.selectBoardList(currentBoardCode, rowBounds);
		
		// 생성한 pagination, boardList 넣어주기
		map.put("pagination", pagination);
		map.put("boardList", boardList);
		
		// pagination, boardList 들어있는 map 반환
		return map;
	}

	/** 게시글 좋아요 체크/해제
	 * @author 허배령
	 */
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

	/** 게시글 상세 조회 서비스
	 * @author 허배령
	 */
	@Override
	public Board selectOne(Map<String, Integer> map) {
		
		Board board = mapper.selectOne(map);
		
		if(board != null) {
	        
	        // 2) 해당 게시글의 이미지 목록 조회
	        List<BoardImg> imageList = mapper.selectImageList(map.get("boardNo"));
	        board.setImageList(imageList);
	        
	    }
	    
	    return board;
	}
	

	/** 조회수 1 증가 서비스
	 * @author 허배령
	 */
	@Override
	public int updateReadCount(int boardNo) {
		
		// 1. 조회 수 1 증가 (UPDATE)
		int result = mapper.updateReadCount(boardNo);
		
		// 2. 현재 조회 수 조회
		if(result > 0) {
			return mapper.selectReadCount(boardNo);
		}
		
		// 실패한 경우 -1 반환
		return -1;
		
		
	}
}
