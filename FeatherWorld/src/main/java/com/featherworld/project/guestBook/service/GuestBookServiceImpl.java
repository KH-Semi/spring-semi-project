package com.featherworld.project.guestBook.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.featherworld.project.common.dto.Pagination;
import com.featherworld.project.guestBook.model.dto.GuestBook;
import com.featherworld.project.guestBook.model.mapper.GuestBookMapper;

import lombok.extern.slf4j.Slf4j;



@Service
@Transactional(rollbackFor=Exception.class)
@Slf4j
public class GuestBookServiceImpl implements GuestBookService{

	@Autowired
	private GuestBookMapper mapper;
	
//		//방명록 조회
//		@Override
//		public List<GuestBook> selectGuestBookList(int memberNo) {
//			return mapper.selectGuestBookList(memberNo);
//		}
//	
//		//방명록 작성
//		@Override
//		public int guestBookInsert(GuestBook guestBook) throws Exception {
//			return mapper.guestBookInsert(guestBook);
//		}
//		
//		//방명록 수정
//		@Override
//		public int guestBookUpdate(GuestBook guestBook) throws Exception {
//			return mapper.guestBookUpdate(guestBook);
//		}
//		
////		//방명록 삭제
////		@Override
////		public int guestBookDelete(int guestBookNo) {
////			return mapper.guestBookDelete(guestBookNo);
////		}
//
//		// 방명록의 않은 게시글 목록 조회/해당 pagination 객체 반환
//		@Override
//		public Map<String, Object> selectGuestBookList(int ownerNo, int cp) {
//			
//			// 0. 반환할 Map 인스턴스(객체) 생성
//			Map<String, Object> map = new HashMap<>();
//			
//			
//			// 1. 방명록의 삭제되지 않은 총 게시글 개수(listCount) 조회
//			int listCount = mapper.getListCount(ownerNo);
//			
//			// 2.방명록이 존재하지 않는다면, 빈 map return
//			if(listCount == 0) return map;
//			
//			// 3. 현재 페이지(cp), 총 게시글 개수(listCount)를 기준으로
//			// 		pagination 객체 생성
//			Pagination pagination = new Pagination(cp, listCount);
//			
//			// 4. 생성된 pagination 객체의 필드값(limit)을 기준으로
//			// 		해당 페이지에 포함되는 게시글 목록만 가져옴
//			//		RowBounds 객체(MyBatis 제공) 활용
//			int limit = pagination.getLimit();
//			int offset = (cp - 1) * limit;
//			RowBounds rowBounds = new RowBounds(offset, limit);
//			
//			// RowBounds와 현재 게시판 종류 번호(currentBoardCode)를 매개변수로 지정
//			// rowBounds 순서는 반드시 두 번째!
//			List<GuestBook> GuestBookList = mapper.selectGuestBookList(ownerNo, rowBounds);
//			
//			// 생성한 pagination, boardList 넣어주기
//			map.put("pagination", pagination);
//			map.put("boardList", GuestBookList);
//			
//			// pagination, boardList 들어있는 map 반환
//			return map;
//		}
//
//		@Override
//		public int guestBookDelete(Map<String, Integer> map) {
//			
//			return mapper.guestBookDelete(map);
//		}
//
//		@Override
//		public GuestBook selectOne(int guestBookNo) {
//			// TODO Auto-generated method stub
//			return null;
//		}
//
//		@Override
//		public int guestBookDelete(int guestBookNo) {
//			// TODO Auto-generated method stub
//			return 0;
//		}
//
	@Override
	public Map<String, Object> selectGuestBookList(int memberNo, int cp) {
		
		Map<String, Object> map = new HashMap<>();
		
		// 1. 방명록 총 개수 조회
		int listCount = mapper.getGuestBookCount(memberNo);
		
		// 2. 방명록이 없으면 빈 데이터 반환
		if (listCount == 0) {
			map.put("guestBookList", List.of());
			map.put("pagination", null);
			return map;
		}
		
		// 3. 페이징 객체 생성 (한 페이지에 3개씩, 페이지 버튼 5개씩)
		Pagination pagination = new Pagination(cp, listCount, 3, 5);
		
		// 4. RowBounds로 페이징 처리
		int limit = pagination.getLimit();
		int offset = (cp - 1) * limit;
		RowBounds rowBounds = new RowBounds(offset, limit);
		
		// 5. 방명록 목록 조회
		List<GuestBook> guestBookList = mapper.selectGuestBookList(memberNo, rowBounds);
		
		// 6. 결과 담기
		map.put("guestBookList", guestBookList);
		map.put("pagination", pagination);
		
		return map;
	}

	@Override
	public int guestBookInsert(GuestBook guestBook) {
		return mapper.guestBookInsert(guestBook);
	}

	@Override
	public int guestBookUpdate(GuestBook guestBook) {
		return mapper.guestBookUpdate(guestBook);
	}

	@Override
	public int guestBookDelete(int guestBookNo) {
		return mapper.guestBookDelete(guestBookNo);
	}

}
