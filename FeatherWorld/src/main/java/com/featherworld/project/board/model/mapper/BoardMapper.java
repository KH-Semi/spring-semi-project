package com.featherworld.project.board.model.mapper;

import java.util.List;


import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.session.RowBounds;

import com.featherworld.project.board.model.dto.Board;
import com.featherworld.project.board.model.dto.BoardType;

import java.util.Map;


import org.apache.ibatis.annotations.Param;




@Mapper
public interface BoardMapper {


	int checkMember(int memberNo);

	List<BoardType> selectBoardType(int memberNo);

	int getListCount(int currentBoardCode);

	List<Board> selectBoardList(int currentBoardCode, RowBounds rowBounds);
	


    // 게시글 좋아요 해제
	int deleteBoardLike(Map<String, Integer> map);
	
	// 게시글 좋아요 체크
	int insertBoardLike(Map<String, Integer> map);

	// 게시글 좋아요 개수 조회
	int selectLikeCount(Integer integer);


}
