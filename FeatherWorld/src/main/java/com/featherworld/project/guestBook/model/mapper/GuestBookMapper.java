package com.featherworld.project.guestBook.model.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.featherworld.project.guestBook.model.dto.GuestBook;

/**
 * 
 */
@Mapper
public interface GuestBookMapper {

	
	/** 방명록 조회
	 * @author 
	 * @param memberNo
	 * @return
	 */
	List<GuestBook> selectGuestBookList(@Param("memberNo") int memberNo);
	
	/** 방명록 작성
	 * @author 
	 * @param inputGuestBook
	 * @return 해당 방명록의 GuestBookNo
	 */
	int guestBookInsert(GuestBook guestBook);

	/** 방명록 수정
	 * @author 
	 * @param inputGuestBook
	 * @return
	 */
	int guestBookUpdate(GuestBook guestBook);

	/** 방명록 삭제
	 * @author 
	 * @param guestBookNo
	 * @return
	 */
	int guestBookDelete(int guestBookNo);
}
