package com.featherworld.project.guestBook.model.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.featherworld.project.guestBook.model.dto.GuestBook;

@Mapper
public interface GuestBookMapper {

	/** 방명록 조회
	 * @param ownerNo
	 * @param loginMemberNo
	 * @param cp
	 * @return
	 */
	List<GuestBook> selectGuestBookList(int ownerNo, int loginMemberNo, int cp);

	
	/** 방명록 작성
	 * @param inputGuestBook
	 * @return 해당 방명록의 GuestBookNo
	 */
	int guestBookInsert(GuestBook GuestBook);

	
	/** 방명록 수정
	 * @param inputGuestBook
	 * @return 
	 */
	int guestBookUpdate(GuestBook inputGuestBook);
	
	

	/** 방명록 삭제
	 * @param map
	 * @return
	 */
	int guestBookDelete(Map<String, Integer> map);


	GuestBook selectOne(int guestBookNo);
}
