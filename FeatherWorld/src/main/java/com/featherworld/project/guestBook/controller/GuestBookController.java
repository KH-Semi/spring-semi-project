package com.featherworld.project.guestBook.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttribute;

import com.featherworld.project.guestBook.model.dto.GuestBook;
import com.featherworld.project.guestBook.service.GuestBookService;
import com.featherworld.project.member.model.dto.Member;
import com.featherworld.project.member.model.service.MemberService;

import lombok.extern.slf4j.Slf4j;

@Controller
@RequestMapping("guestBook")
@Slf4j
public class GuestBookController {

	@Autowired
	private GuestBookService service;


	// 방명록 조회
	@GetMapping("")
	public String guestBookPage(@SessionAttribute(value = "loginMember", required = false) Member loginMember,
			@RequestParam(value = "cp", required = false, defaultValue = "1") int cp, Model model) {

		int ownerNo;
		int loginMemberNo;

		
		 if (loginMember != null) {
		        ownerNo = loginMember.getMemberNo();
		        loginMemberNo = loginMember.getMemberNo();
		    }
		 else {
		        // 비회원이 들어온 경우 기본값 설정
		        ownerNo = 1;           // 예: 홈페이지 주인 번호
		        loginMemberNo = -1;    // 비회원 표시용
		    }
		
		
		
		// 2. 방명록 목록 조회
		List<GuestBook> guestBookList = service.selectGuestBookList(ownerNo, loginMemberNo, cp);

		model.addAttribute("member", loginMember);
		model.addAttribute("guestBookList", guestBookList);
		model.addAttribute("cp", cp);
		model.addAttribute("owner", loginMember); // 현재 홈피 주인 정보를 전달 ??(GPT)

		return "guestBook/guestBook"; // templates/guestBook/guestBook.html
	}

	// 방명록 작성
	@PostMapping("/insert")
	public String insertGuestBook(@SessionAttribute("loginMember") Member loginMember,
			GuestBook inputGuestBook,
			@RequestParam(value="cp",required=false,defaultValue="1")int cp) throws Exception {
		
		 	inputGuestBook.setVisitorNo(loginMember.getMemberNo()); // 작성자 번호
		    inputGuestBook.setOwnerNo(loginMember.getMemberNo());   // 본인 미니홈피 기준

		    int result = service.guestBookInsert(inputGuestBook);
		
		    return "redirect:/guestbook?cp=" + cp;
	}

	// 수정
	@PostMapping("/update")
	public String updateGuestBook(@SessionAttribute("loginMember") Member loginMember,
								   GuestBook inputGuestBook,
			@RequestParam(value = "cp" , required=false, defaultValue="1")int cp) throws Exception {
		
		
		// 작성자 본인인지 검증 ( 글 번호로 기존 방명록 글 가져오기)
	    GuestBook origin = service.selectOne(inputGuestBook.getGuestBookNo());

	    
	    //2.없거나 작성자가 다르면 X 
	    if (origin == null || origin.getVisitorNo() != loginMember.getMemberNo()) {
	        return "redirect:/guestbook?cp=" + cp;
	    }

	    
	    //수정
	    inputGuestBook.setVisitorNo(loginMember.getMemberNo());

	    int result = service.guestBookUpdate(inputGuestBook);

	    return "redirect:/guestbook?cp=" + cp;
	}

	// 삭제
	@PostMapping("/delete")
	public String deleteGuestBook( @SessionAttribute("loginMember") Member loginMember,
	        @RequestParam("guestBookNo") int guestBookNo,
	        @RequestParam(value = "cp", required = false, defaultValue = "1") int cp) {
		  GuestBook guestBook = service.selectOne(guestBookNo);
		    int memberNo = loginMember.getMemberNo();

		    // 작성자 or 홈피 주인만 삭제 가능
		    if (guestBook == null ||
		        (guestBook.getVisitorNo() != memberNo && guestBook.getOwnerNo() != memberNo)) {
		        return "redirect:/guestbook?cp=" + cp;
		    }

		    Map<String, Integer> map = new HashMap<>();
		    map.put("guestBookNo", guestBookNo);
		    map.put("memberNo", memberNo);

		    int result = service.guestBookDelete(map);

		    return "redirect:/guestbook?cp=" + cp;
	}

}
