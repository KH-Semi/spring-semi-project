package com.featherworld.project.board.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Board {
	
	private int boardNo;			// 게시글 번호
	private String boardTitle;		// 게시글 제목
	private String boardContent;	// 게시글 내용
	private String boardWriteDate;	// 게시글 작성일
	private String boardUpdateDate;	// 게시글 수정일
	private int readCount;			// 게시글 조회수
	private String boardDelFl;		// 게시글 삭제 여부 (Y/N)
	private int boardCode;			// 게시판 종류 번호
	private int memberNo;			// 회원 번호
	
	// 게시판 종류 테이블과 조인
	private String boardName;		// 게시판 종류 이름
	private int authority;			// 게시판 종류 접근 권한 (0:전체 , 1:일촌)
	
	// 좋아요 여부 확인
	private int likeCheck;

}
