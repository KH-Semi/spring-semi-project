package com.featherworld.project.guestBook.service;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.featherworld.project.guestBook.model.dto.GuestBook;

public interface GuestBookService {

	 
//	/** 방명록 조회 서비스 
//	 * @param memberNo
//	 * @return
//	 */
//	List<GuestBook> selectGuestBookList(@Param("memberNo") int memberNo);
//	
//	/** 방명록 작성 서비스 
//	 * @param inputGuestBook
//	 * @return
//	 * @throws Exception
//	 */
//	int guestBookInsert(GuestBook guestBook) throws Exception;
//	
//	/** 방명록 수정 서비스 
//	 * @param inputGuestBook
//	 * @return
//	 * @throws Exception
//	 */
//	int guestBookUpdate(GuestBook guestBook) throws Exception;
//	
//	/** 방명록 삭제 서비스 
//	 * @param map
//	 * @return
//	 */
//	int guestBookDelete(Map<String, Integer> map);
//
//	/** 비동기로 방명록 조회 서비스
//	 * @param memberNo
//	 * @param cp
//	 * @return
//	 */
//	Map<String, Object> selectGuestBookList(int memberNo, int cp);
//
//	GuestBook selectOne(int guestBookNo);
//
//	int guestBookDelete(int guestBookNo);
	
	/**
	 * 방명록 목록 조회 (페이징 포함)
	 * @param memberNo 홈피 주인 번호
	 * @param cp 현재 페이지
	 * @return 방명록 목록과 페이징 정보
	 */
	Map<String, Object> selectGuestBookList(int memberNo, int cp);
	
	/**
	 * 방명록 작성
	 * @param guestBook 방명록 정보
	 * @return 작성 결과 (성공 시 1, 실패 시 0)
	 * @throws Exception
	 */
	int guestBookInsert(GuestBook guestBook);
	
	/**
	 * 방명록 수정
	 * @param guestBook 수정할 방명록 정보
	 * @return 수정 결과 (성공 시 1, 실패 시 0)
	 * @throws Exception
	 */
	int guestBookUpdate(GuestBook guestBook);
	
	/**
	 * 방명록 삭제
	 * @param guestBookNo 삭제할 방명록 번호
	 * @return 삭제 결과 (성공 시 1, 실패 시 0)
	 */
	int guestBookDelete(int guestBookNo);

}
