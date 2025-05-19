package com.featherworld.project.guestBook.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttribute;

import com.featherworld.project.guestBook.model.dto.GuestBook;
import com.featherworld.project.guestBook.service.GuestBookService;
import com.featherworld.project.member.model.dto.Member;


@Controller


public class GuestBookController {

	@Autowired
	private GuestBookService service;

	
	
	 /** 동기
	  * 해당 회원의 삭제되지 않은 방명록 목록 조회
	  * @author 
	 * @return
	 */
	@GetMapping("/{memberNo:[0-9]+}/guestbook")
	    public String guestBookPage(@SessionAttribute(value = "loginMember", required = false) Member loginMember,
	    		@PathVariable int memberNo,
	    		@RequestParam(value = "ownerNo", required = false) Integer ownerNo,
	    		@RequestParam(value = "cp", required = false, defaultValue = "1") int cp,
	    		Model model) {
		
		
		 int loginMemberNo = (loginMember != null) ? loginMember.getMemberNo() : -1;

		    if (ownerNo == null) {
		        ownerNo = loginMemberNo;
		    }

		    Map<String, Integer> map = new HashMap<>();
		    
		    map.put("ownerNo", ownerNo);
		    map.put("loginMemberNo", loginMemberNo);
		    map.put("cp", cp);
		    
		   List<GuestBook> guestBookList = service.selectGuestBookList(map); 		

		    //GuestBookService.selectGuestBookList(ownerNo, loginMemberNo, cp);
		    
		    model.addAttribute("guestBookList", map.get("guestBookList"));
		    model.addAttribute("pagination", map.get("pagination"));
		    model.addAttribute("ownerInfo", map.get("ownerInfo"));
		    model.addAttribute("canWrite", map.get("canWrite"));
		
		
	        return "guestbook/guestbook";  // templates/guestBook/guestBook.html
	    }
	 
	 
	 
	 
	 
	 
	 
	 
//	// 방명록 조회
//	@GetMapping("")
//	public List<GuestBook>selectGuestBookList(
//			@SessionAttribute(value = "loginMember", required = false) Member loginMember,
//			 @RequestParam(value = "ownerNo", required = false) Integer ownerNo,
//			@RequestParam(value = "cp", required = false, defaultValue = "1") int cp) {
//
//		
//		int loginMemberNo = (loginMember!=null)? loginMember.getMemberNo():-1;
//		
//		//ownerNo가 없을 경우 
//		if(ownerNo==null) {
//			if(loginMember !=null) {
//				//로그인 했으면 본인의 미니홈피라고 간주하여 ownerNo를 본인의 번호로 설정
//				ownerNo = loginMember.getMemberNo();
//				
//			}else {
//				//로그인도 안하고, ownerNo도 없는 비회원
//				ownerNo = -1;
//			}
//		}
//		
//		return service.selectGuestBookList(ownerNo, loginMemberNo, cp);
//	}

	// 방명록 작성
	@PostMapping("")
	public int insertGuestBook(@SessionAttribute(value = "loginMember",required=false) Member loginMember,
			GuestBook inputGuestBook,
			@RequestParam(value="cp",required=false,defaultValue="1")int cp) throws Exception {
		
		 // 1. 로그인 체크
	    if (loginMember == null) return 0;

	    // 2. 작성자/홈피 주인 정보 설정
	    inputGuestBook.setVisitorNo(loginMember.getMemberNo());
	    inputGuestBook.setOwnerNo(loginMember.getMemberNo());

	    // 3. 삽입 후 결과 반환 (성공 시 1)
	    return service.guestBookInsert(inputGuestBook);
	}

	// 수정
	@PutMapping("")
	public int updateGuestBook(@SessionAttribute(value = "loginMember",required=false) Member loginMember,
								   GuestBook inputGuestBook,
			@RequestParam(value = "cp" , required=false, defaultValue="1")int cp) throws Exception {
		
		
		// 로그인 안 한 경우
	    if (loginMember == null) return 0;

	    // 기존 글 가져오기
	    GuestBook origin = service.selectOne(inputGuestBook.getGuestBookNo());

	    // 존재하지 않거나 작성자가 본인이 아니면 실패
	    if (origin == null || origin.getVisitorNo() != loginMember.getMemberNo()) {
	        return 0;
	    }

	    // 작성자 번호 세팅 후 수정 수행
	    inputGuestBook.setVisitorNo(loginMember.getMemberNo());
	    return service.guestBookUpdate(inputGuestBook);
	}

	// 삭제
	@DeleteMapping("")
	public int deleteGuestBook( @SessionAttribute(value="loginMember",required=false) Member loginMember,
	        @RequestParam("guestBookNo") int guestBookNo,
	        @RequestParam(value = "cp", required = false, defaultValue = "1") int cp) {
		
		
		//로그인 안한 경우 차단
		if(loginMember==null) {
			return 0;
		}
		
		GuestBook guestBook = service.selectOne(guestBookNo);
		int memberNo = loginMember.getMemberNo();
		
		 // 작성자 or 홈피 주인만 삭제 가능
	    if (guestBook == null ||
	        (guestBook.getVisitorNo() != memberNo && guestBook.getOwnerNo() != memberNo)) {
	        return 0;
	    }

	    Map<String, Integer> map = new HashMap<>();
	    map.put("guestBookNo", guestBookNo);
	    map.put("memberNo", memberNo);
		
		    return service.guestBookDelete(map);
	}

}
