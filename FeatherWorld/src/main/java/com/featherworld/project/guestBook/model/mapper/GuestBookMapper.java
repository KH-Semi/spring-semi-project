package com.featherworld.project.guestBook.model.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

import com.featherworld.project.guestBook.model.dto.GuestBook;

/**
 * 
 */
@Mapper
public interface GuestBookMapper {

	
//	/** 방명록 조회
//	 * @author 
//	 * @param memberNo
//	 * @return
//	 */
//	List<GuestBook> selectGuestBookList(@Param("memberNo") int memberNo);
//	
//	/** 방명록 작성
//	 * @author 
//	 * @param inputGuestBook
//	 * @return 해당 방명록의 GuestBookNo
//	 */
//	int guestBookInsert(GuestBook guestBook);
//
//	/** 방명록 수정
//	 * @author 
//	 * @param inputGuestBook
//	 * @return
//	 */
//	int guestBookUpdate(GuestBook guestBook);
//
//	/** 방명록 삭제
//	 * @author 
//	 * @param map
//	 * @return
//	 */
//	int guestBookDelete(Map<String, Integer> map);
//	
//	
//	
//	/** 비동기 조회
//	 * @param memberNo
//	 * @param offset
//	 * @param limit
//	 * @return
//	 */
//	List<GuestBook> selectGuestBookListPaging(@Param("memberNo") int memberNo,
//	                                        @Param("offset") int offset,
//	                                        @Param("limit") int limit);
//
//
//
//	int getListCount(int ownerNo);
//
//	List<GuestBook> selectGuestBookList(@Param("ownerNo") int ownerNo, RowBounds rowBounds);
//	
	
	/**
	 * 방명록 목록 조회 (작성자 정보 포함, 페이징)
	 * @param memberNo 홈피 주인 번호
	 * @param rowBounds 페이징 정보
	 * @return 방명록 목록
	 */
	List<GuestBook> selectGuestBookList(@Param("memberNo") int memberNo, RowBounds rowBounds);
	
	/**
	 * 방명록 총 개수 조회
	 * @param memberNo 홈피 주인 번호
	 * @return 방명록 총 개수
	 */
	int getGuestBookCount(@Param("memberNo") int memberNo);
	
	/**
	 * 방명록 작성
	 * @param guestBook 방명록 정보
	 * @return 작성된 행 수
	 */
	int guestBookInsert(GuestBook guestBook);
	
	/**
	 * 방명록 삭제
	 * @param guestBookNo 방명록 번호
	 * @return 삭제된 행 수
	 */
	int guestBookDelete(@Param("guestBookNo") int guestBookNo);
	
	/**
	 * 방명록 수정
	 * @param guestBook 수정할 방명록 정보
	 * @return 수정된 행 수
	 */
	int guestBookUpdate(GuestBook guestBook);

	GuestBook selectOne(Integer guestBookNo);
}
