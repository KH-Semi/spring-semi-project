package com.featherworld.project.guestBook.model.mapper;

import java.util.Map;

import com.featherworld.project.guestBook.model.dto.GuestBook;

public interface EditGuestBookMapper {

	/** 게시글 작성
	 * @param inputGuestBook
	 * @return 해당 게시글의 GuestBookNo
	 */
	int guestBookInsert(GuestBook inputGuestBook);

	
	/** 게시글 부분( 제목/내용) 수정
	 * @param inputGuestBook
	 * @return
	 */
	int guestBookUpdate(GuestBook inputGuestBook);
	
	

	/** 게시글 삭제
	 * @param map
	 * @return
	 */
	int guestBookDelete(Map<String, Integer> map);
	
}
