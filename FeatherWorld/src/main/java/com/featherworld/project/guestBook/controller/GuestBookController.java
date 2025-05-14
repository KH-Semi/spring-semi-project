package com.featherworld.project.guestBook.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttribute;

import com.featherworld.project.guestBook.model.dto.GuestBook;
import com.featherworld.project.guestBook.service.GuestBookService;
import com.featherworld.project.member.model.dto.Member;

import lombok.extern.slf4j.Slf4j;

@Controller
@RequestMapping("guestbook")
@Slf4j
public class GuestBookController {

	
	@Autowired
	private GuestBookService service;
	
	@Autowired
	private MemberService memberService;
	
	 @GetMapping("/guestbook")
	    public String guestBookPage(@SessionAttribute("loginMember")Member loginMember,Model model) {

	       int memberNo = loginMember.getMemberNo();//로그인 한 회원의 정보 = ownerNo

	       model.addAttribute("member", loginMember);
	       
	       
	        // 2. 방명록 목록 조회
	        List<GuestBook> guestBookList = GuestBookService.selectGuestbookList(memberNo);
	        model.addAttribute("guestBookList", guestBookList);

	        return "guestBook/guestBook"; // templates/guestBook/guestBook.html
	    }
	
	
}
