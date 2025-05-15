package com.featherworld.project.guestBook.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.featherworld.project.guestBook.model.dto.GuestBook;
import com.featherworld.project.guestBook.model.mapper.GuestBookMapper;

import lombok.extern.slf4j.Slf4j;

@Service
@Transactional(rollbackFor=Exception.class)
@Slf4j
public class GuestBookServiceImpl implements GuestBookService{

	@Autowired
	private GuestBookMapper mapper;
	
	public List<GuestBook> selectGuestBookList(int ownerNo, int loginMemberNo, int cp) {
	
		return mapper.selectGuestBookList(ownerNo,loginMemberNo,cp);
	}
	
}
