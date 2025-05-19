package com.featherworld.project.guestBook.service;

import java.util.List;
import java.util.Map;

import com.featherworld.project.guestBook.model.dto.GuestBook;

public interface GuestBookService {

	 
	/** 방명록 조회
	 * @author JINJIN
	 * @param ownerNo
	 * @param loginMemberNo
	 * @param cp
	 * @return
	 */
	Map<String, Object> selectGuestBookList(Integer ownerNo, int loginMemberNo, int cp);
	
	
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
	
	
	
	/** 본인이 작성한 글이 아니면 수정 못하게
	 * @param guestBookNo
	 * @return
	 */
	GuestBook selectOne(int guestBookNo);

	
	
}
