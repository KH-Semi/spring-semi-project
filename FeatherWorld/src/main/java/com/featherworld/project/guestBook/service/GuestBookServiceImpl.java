package com.featherworld.project.guestBook.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.featherworld.project.guestBook.model.dto.GuestBook;
import com.featherworld.project.guestBook.model.mapper.GuestBookMapper;

import lombok.extern.slf4j.Slf4j;

@Service
@Transactional(rollbackFor=Exception.class)
public class GuestBookServiceImpl implements GuestBookService{

	@Autowired
	private GuestBookMapper mapper;
	
	
		//방명록 조회
		public List<GuestBook> selectGuestBookList(int ownerNo, int loginMemberNo, int cp) {
	
			return mapper.selectGuestBookList(ownerNo,loginMemberNo,cp);
		}
	
	
	
		//방명록 작성
		@Override
		public int guestBookInsert(GuestBook inputGuestBook) throws Exception {
			int result = mapper.guestBookInsert(inputGuestBook);
			return result;
		}

		
		//방명록 수정
		@Override
		public int guestBookUpdate(GuestBook inputGuestBook) throws Exception {
				
			int result = mapper.guestBookUpdate(inputGuestBook);
			
			if(result ==0) {
				return 0;
			}
			//성공한 경우
			return result;
			
		}
		
		//방명록 삭제
		@Override
		public int guestBookDelete(Map<String, Integer> map) {
			return mapper.guestBookDelete(map);
		}


		//본인이 작성한 글이 아니면 수정 못하게
		@Override
		public GuestBook selectOne(int guestBookNo) {
			return mapper.selectOne(guestBookNo);
		}
}
