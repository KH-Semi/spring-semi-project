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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttribute;

import com.featherworld.project.guestBook.model.dto.GuestBook;
import com.featherworld.project.guestBook.service.GuestBookService;
import com.featherworld.project.member.model.dto.Member;


@Controller
@RequestMapping
public class GuestBookController {

	@Autowired
	private GuestBookService service;
	
	
	 /** 동기
	  * 해당 회원의 삭제되지 않은 방명록 목록 조회
	  * @author 
	 * @return
	 */
	@GetMapping("{memberNo:[0-9]+}/guestbook")
	    public String guestBookPage(
	    		@PathVariable("memberNo") int memberNo,
	    		@SessionAttribute(value = "loginMember", required = false) Member loginMember,
	    		@RequestParam(value = "cp", required = false, defaultValue = "1") int cp,
	    		Model model) {
		    
		 		
		  // Map<String, Object> map = service.selectGuestBookList(memberNo, cp);
		   List<GuestBook> guestBookList = service.selectGuestBookList(memberNo);
		   //List<GuestBook> guestBookList = (List<GuestBook>) map.get("guestBookList");
		   
		    model.addAttribute("guestBookList",guestBookList);
		    model.addAttribute("loginMember",loginMember);
		    model.addAttribute("ownerNo",memberNo);
		    
		    
		   
		   // model.addAttribute("pagination", map.get("pagination"));
		    
		    
	        return "guestBook/guestBook";  // templates/guestBook/guestBook.html
	    }
	
//		/** 비동기로 방명록 목록
//	    * @author 
//	    * @param guestbookno
//	    * @param cp 현재 페이지 번호
//	    */
//	   @ResponseBody
//	   @GetMapping("{memberNo:[0-9]+}/guestbook")
//	   public Map<String, Object> selectBoardList(@PathVariable  ("guestBookNo") int guestBookNo,
//	                                   @RequestParam(value = "cp", required = false, defaultValue = "1") int cp) {
//
//	      return service.selectGuestBookList(guestBookNo, cp);
//	   }
//	
//	
//	
//	
//	


	// 방명록 작성

	@PostMapping("{memberNo:[0-9]+}/guestbook")
	@ResponseBody
	public Map<String, Object> insertGuestBook(@SessionAttribute(value = "loginMember",required=false) Member loginMember,
			@RequestBody GuestBook guestBook,
			@RequestParam(value="cp",required=false,defaultValue="1")int cp,
			@PathVariable("memberNo")int memberNo) throws Exception {
		
		
		 Map<String, Object> map = new HashMap<>();
		 // 1. 로그인 체크
	   // if (loginMember == null) return 0;
		 
		 if (loginMember == null) {
		        map.put("result", 0);
		        return map;
		    }

	    // 2. 작성자/홈피 주인 정보 설정
	    guestBook.setVisitorNo(loginMember.getMemberNo());
	    guestBook.setOwnerNo(memberNo);

	    // 3. 삽입 후 결과 반환 (성공 시 1)
	    //return service.guestBookInsert(guestBook);
	    
	    int result = service.guestBookInsert(guestBook);
	    map.put("result", result);
	    return map;
	}
	
	
	
	
	
	
	

//	// 수정
//	@PutMapping("{memberNo:[0-9]+}/guestbook")
//	public int updateGuestBook(@SessionAttribute(value = "loginMember",required=false) Member loginMember,
//								   GuestBook inputGuestBook,
//			@RequestParam(value = "cp" , required=false, defaultValue="1")int cp ,
//			@PathVariable("memberNo")int memberNo) throws Exception {
//		
//		
//		// 로그인 안 한 경우
//	    if (loginMember == null) return 0;
//
//	    // 기존 글 가져오기
//	    GuestBook origin = service.selectOne(inputGuestBook.getGuestBookNo());
//
//	    // 존재하지 않거나 작성자가 본인이 아니면 실패
//	    if (origin == null || origin.getVisitorNo() != loginMember.getMemberNo()) {
//	        return 0;
//	    }
//
//	    // 작성자 번호 세팅 후 수정 수행
//	    inputGuestBook.setVisitorNo(loginMember.getMemberNo());
//	    return service.guestBookUpdate(inputGuestBook);
//	}
//
//	// 삭제
//	@DeleteMapping("{memberNo:[0-9]+}/guestbook")
//	public int deleteGuestBook( @SessionAttribute(value="loginMember",required=false) Member loginMember,
//	        @RequestParam("guestBookNo") int guestBookNo,
//	        @RequestParam(value = "cp", required = false, defaultValue = "1") int cp ,
//	        @PathVariable("memberNo")int memberNo) {
//		
//		
//		//로그인 안한 경우 차단
//		if(loginMember==null) {
//			return 0;
//		}
//		
//		GuestBook guestBook = service.selectOne(guestBookNo);
//		int loginMemberNo = loginMember.getMemberNo();
//		
//		 // 작성자 or 홈피 주인만 삭제 가능
//	    if (guestBook == null ||
//	        (guestBook.getVisitorNo() != loginMemberNo 
//	        && guestBook.getOwnerNo() != loginMemberNo)) {
//	        return 0;
//	    }
//
//	    Map<String, Integer> map = new HashMap<>();
//	    map.put("guestBookNo", guestBookNo);
//	    map.put("memberNo", memberNo);
//		
//		    return service.guestBookDelete(map);
//	}

}
