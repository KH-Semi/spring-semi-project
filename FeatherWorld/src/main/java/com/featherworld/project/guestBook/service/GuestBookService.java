package com.featherworld.project.guestBook.service;

import java.util.List;

import com.featherworld.project.guestBook.model.dto.GuestBook;

public interface GuestBookService {

	 /** 방명록 조회
	 * @param ownerNo
	 * @param loginMemberNo
	 * @param cp
	 * @return
	 */
	List<GuestBook> selectGuestBookList(int ownerNo, int loginMemberNo, int cp);

	

	

	
}
