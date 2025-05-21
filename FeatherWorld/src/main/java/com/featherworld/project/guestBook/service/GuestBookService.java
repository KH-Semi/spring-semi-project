package com.featherworld.project.guestBook.service;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.featherworld.project.guestBook.model.dto.GuestBook;

public interface GuestBookService {

	 
	/** 방명록 조회 서비스 
	 * @param memberNo
	 * @return
	 */
	List<GuestBook> selectGuestBookList(@Param("memberNo") int memberNo);
	
	/** 방명록 작성 서비스 
	 * @param inputGuestBook
	 * @return
	 * @throws Exception
	 */
	int guestBookInsert(GuestBook guestBook) throws Exception;
	
	/** 방명록 수정 서비스 
	 * @param inputGuestBook
	 * @return
	 * @throws Exception
	 */
	int guestBookUpdate(GuestBook guestBook) throws Exception;
	
	/** 방명록 삭제 서비스 
	 * @param map
	 * @return
	 */
	int guestBookDelete(int guestBookNo);

	//Map<String, Object> selectGuestBookList(int memberNo, int cp);

}
