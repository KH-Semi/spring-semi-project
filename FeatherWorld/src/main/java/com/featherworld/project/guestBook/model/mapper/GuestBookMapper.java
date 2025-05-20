package com.featherworld.project.guestBook.model.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.featherworld.project.guestBook.model.dto.GuestBook;

@Mapper
public interface GuestBookMapper {

	
	
	/** 방명록 조회
	 * @author 
	 * @param ownerNo
	 * @param loginMemberNo
	 * @param cp
	 * @return
	 */
	//Map<String, Object> selectGuestBookList(map);
	
	/** 방명록 작성
	 * @author 
	 * @param inputGuestBook
	 * @return 해당 방명록의 GuestBookNo
	 */
	int guestBookInsert(GuestBook GuestBook);

	
	/** 방명록 수정
	 * @author 
	 * @param inputGuestBook
	 * @return 
	 */
	int guestBookUpdate(GuestBook inputGuestBook);
	
	

	/** 방명록 삭제
	 * @author 
	 * @param map
	 * @return
	 */
	int guestBookDelete(Map<String, Integer> map);


	GuestBook selectOne(int guestBookNo);


	
}
