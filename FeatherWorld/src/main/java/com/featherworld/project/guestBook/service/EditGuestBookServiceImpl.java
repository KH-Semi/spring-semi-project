package com.featherworld.project.guestBook.service;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.featherworld.project.guestBook.model.dto.GuestBook;
import com.featherworld.project.guestBook.model.mapper.EditGuestBookMapper;

import lombok.extern.slf4j.Slf4j;

@Service
@Transactional(rollbackFor=Exception.class)
@PropertySource("classpath:/config.properties")
@Slf4j
public class EditGuestBookServiceImpl implements EditGuestBookService {

	
	@Autowired
	private EditGuestBookMapper mapper;

	
	//게시글 작성
	@Override
	public int guestBookInsert(GuestBook inputGuestBook) throws Exception {
		return 0;
	}

	
	//게시글 수정
	@Override
	public int guestBookUpdate(GuestBook inputGuestBook) throws Exception {
		return 0;
	}

	//게시글 수정
	@Override
	public int guestBookUpdate(GuestBook inputGuestBook, String deleteOrderList) throws Exception {
		return 0;
	}
	
	
	
	//게시글 삭제
	@Override
	public int guestBookDelete(Map<String, Integer> map) {
		return 0;
	}


	
	
	
	
	
}
