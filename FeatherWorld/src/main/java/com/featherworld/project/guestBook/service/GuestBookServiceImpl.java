package com.featherworld.project.guestBook.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.featherworld.project.guestBook.model.dto.GuestBook;
import com.featherworld.project.guestBook.model.mapper.GuestBookMapper;



@Service
@Transactional(rollbackFor=Exception.class)
public class GuestBookServiceImpl implements GuestBookService{

	@Autowired
	private GuestBookMapper mapper;
	
		//방명록 조회
		@Override
		public List<GuestBook> selectGuestBookList(int memberNo) {
			return mapper.selectGuestBookList(memberNo);
		}
	
		//방명록 작성
		@Override
		public int guestBookInsert(GuestBook guestBook) throws Exception {
			return mapper.guestBookInsert(guestBook);
		}
		
		//방명록 수정
		@Override
		public int guestBookUpdate(GuestBook guestBook) throws Exception {
			return mapper.guestBookUpdate(guestBook);
		}
		
		//방명록 삭제
		@Override
		public int guestBookDelete(int guestBookNo) {
			return mapper.guestBookDelete(guestBookNo);
		}

//		@Override
//		public Map<String, Object> selectGuestBookList(int guestBookNo, int cp) {
//			// TODO Auto-generated method stub
//			return null;
//		}
//


}
