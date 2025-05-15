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
	
	//@Autowired
	//private MemberService memberService;
	
	 @GetMapping("/{cp}")
	    public String guestBookPage(@SessionAttribute("loginMember")Member loginMember,
	    		Model model,@PathVariable("cp") int cp) {

	       int ownerNo = loginMember.getMemberNo();//로그인 한 회원의 정보 = ownerNo. 현재 내 홈피기준
	       int loginMemberNo = loginMember.getMemberNo();//로그인한 회원의 정보
	     
	       
	       
	        // 2. 방명록 목록 조회
	        List<GuestBook> guestBookList = service.selectGuestBookList(ownerNo,loginMemberNo,cp);
	       
	       
	        model.addAttribute("member", loginMember);
	        model.addAttribute("guestBookList",guestBookList);
	        model.addAttribute("cp",cp);
	        
	        
	        return "guestBook/guestBook"; // templates/guestBook/guestBook.html
	    }
	
	
}
