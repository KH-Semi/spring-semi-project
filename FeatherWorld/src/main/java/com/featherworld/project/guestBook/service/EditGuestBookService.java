package com.featherworld.project.guestBook.service;


import java.util.Map;

import com.featherworld.project.guestBook.model.dto.GuestBook;

public interface EditGuestBookService {

	
	/** 방명록 작성 서비스 
	 * @param inputGuestBook
	 * @return
	 * @throws Exception
	 */
	int guestBookInsert(GuestBook inputGuestBoard) throws Exception;

	/** 방명록 수정 서비스 
	 * @param inputGuestBook
	 * @return
	 */
	int guestBookUpdate(GuestBook inputGuestBook) throws Exception;

	/** 방명록 삭제 서비스 
	 * @param map
	 * @return
	 */
	int guestBookDelete(Map<String, Integer> map);
	
	
	
}
