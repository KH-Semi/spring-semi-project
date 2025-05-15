package com.featherworld.project.guestBook.model.mapper;

import java.util.List;

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

}
