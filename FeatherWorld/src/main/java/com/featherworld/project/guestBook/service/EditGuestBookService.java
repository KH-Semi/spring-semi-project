package com.featherworld.project.guestBook.service;


import java.util.Map;

import com.featherworld.project.guestBook.model.dto.GuestBook;

public interface EditGuestBookService {

	
	/** 게시글 작성 서비스 
	 * @param inputBoard
	 * @return
	 * @throws Exception
	 */
	int guestBookInsert(GuestBook inputBoard) throws Exception;

	/** 게시글 수정 서비스 
	 * @param inputBoard
	 * @param deleteOrderList
	 * @return
	 */
	int guestBookUpdate(GuestBook inputGuestBook, String deleteOrderList) throws Exception;

	/** 게시글 삭제 서비스 
	 * @param map
	 * @return
	 */
	int guestBookDelete(Map<String, Integer> map);
	
	
	
}
