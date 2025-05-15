package com.featherworld.project.board.model.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.featherworld.project.board.model.dto.Board;

@Mapper
public interface BoardMapper {

	// 게시판 종류 목록 조회 (특정 회원의 폴더 목록)
    List<Board> selectBoardTypeList(@Param("memberNo") int memberNo);

    // 게시판 종류 생성
    int insertBoardType(Board board);

    // 게시판 종류 수정
    int updateBoardType(Board board);

    // 게시판 종류 삭제
    int deleteBoardType(@Param("boardCode") int boardCode);

    // 친구공개 게시판 접근 권한 확인
    int checkFriendAccess(@Param("loginMemberNo") int loginMemberNo, @Param("ownerNo") int ownerNo);

    // 게시글 좋아요 해제
	int deleteBoardLike(Map<String, Integer> map);
	
	// 게시글 좋아요 체크
	int insertBoardLike(Map<String, Integer> map);

	// 게시글 좋아요 개수 조회
	int selectLikeCount(Integer integer);

}
