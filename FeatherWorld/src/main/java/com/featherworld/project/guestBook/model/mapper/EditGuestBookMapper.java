package com.featherworld.project.guestBook.model.mapper;

import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.featherworld.project.guestBook.model.dto.GuestBook;
@Mapper
public interface EditGuestBookMapper {

	/** 방명록 작성
	 * @param inputGuestBook
	 * @return 해당 방명록의 GuestBookNo
	 */
	int guestBookInsert(GuestBook inputGuestBook);

	
	/** 방명록 부분( 제목/내용) 수정
	 * @param inputGuestBook
	 * @return 
	 */
	int guestBookUpdate(GuestBook inputGuestBook);
	
	

	/** 방명록 삭제
	 * @param map
	 * @return
	 */
	int guestBookDelete(Map<String, Integer> map);
	
}
