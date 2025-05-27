package com.featherworld.project.guestBook.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

import com.featherworld.project.common.dto.Pagination;
import com.featherworld.project.guestBook.model.dto.GuestBook;
import com.featherworld.project.guestBook.service.GuestBookService;
import com.featherworld.project.member.model.dto.Member;

@Controller
@RequestMapping
public class GuestBookController {

	@Autowired
	private GuestBookService service;

//
//	/**
//	 * 동기 해당 회원의 삭제되지 않은 방명록 목록 조회
//	 * 
//	 * @author
//	 * @return
//	 */
//	@GetMapping("{memberNo:[0-9]+}/guestbook")
//	public String guestBookPage(@PathVariable("memberNo") int memberNo,
//			@SessionAttribute(value = "loginMember", required = false) Member loginMember,
//			@RequestParam(value = "cp", required = false, defaultValue = "1") int cp, Model model) {
//
//		// Map<String, Object> map = service.selectGuestBookList(memberNo, cp);
//		List<GuestBook> guestBookList = service.selectGuestBookList(memberNo);
//		// List<GuestBook> guestBookList = (List<GuestBook>) map.get("guestBookList");
//
//		model.addAttribute("guestBookList", guestBookList);
//		model.addAttribute("loginMember", loginMember);
//		model.addAttribute("ownerNo", memberNo);
//
//		// model.addAttribute("pagination", map.get("pagination"));
//
//		return "guestBook/guestBook"; // templates/guestBook/guestBook.html
//	}
//
//		/** 비동기로 방명록 목록
//	    * @author 
//	    * @param guestbookno
//	    * @param cp 현재 페이지 번호
//	    */	  
//	@GetMapping("{memberNo:[0-9]+}/guestbook/list")
//	@ResponseBody
//	public Map<String, Object> selectGuestBookList(@PathVariable("memberNo") int memberNo,
//			@RequestParam(value = "cp", defaultValue = "1") int cp) {
//
//		return service.selectGuestBookList(memberNo, cp);
//	}
//	
//
//	// 방명록 작성
//
//	@PostMapping("{memberNo:[0-9]+}/guestbook")
//	@ResponseBody
//	public Map<String, Object> insertGuestBook(
//			@SessionAttribute(value = "loginMember", required = false) Member loginMember,
//			@RequestBody GuestBook guestBook, @RequestParam(value = "cp", required = false, defaultValue = "1") int cp,
//			@PathVariable("memberNo") int memberNo) throws Exception {
//
//		Map<String, Object> map = new HashMap<>();
//		// 1. 로그인 체크
//		// if (loginMember == null) return 0;
//
//		if (loginMember == null) {
//			map.put("result", 0);
//			return map;
//		}
//
//		// 2. 작성자/홈피 주인 정보 설정
//		guestBook.setVisitorNo(loginMember.getMemberNo());
//		guestBook.setOwnerNo(memberNo);
//
//		// 3. 삽입 후 결과 반환 (성공 시 1)
//		// return service.guestBookInsert(guestBook);
//
//		int result = service.guestBookInsert(guestBook);
//		map.put("result", result);
//		return map;
//	}
//
////	// 수정
////	@PutMapping("{memberNo:[0-9]+}/guestbook")
////	public int updateGuestBook(@SessionAttribute(value = "loginMember",required=false) Member loginMember,
////								   GuestBook inputGuestBook,
////			@RequestParam(value = "cp" , required=false, defaultValue="1")int cp ,
////			@PathVariable("memberNo")int memberNo) throws Exception {
////		
////		
////		// 로그인 안 한 경우
////	    if (loginMember == null) return 0;
////
////	    // 기존 글 가져오기
////	    GuestBook origin = service.selectOne(inputGuestBook.getGuestBookNo());
////
////	    // 존재하지 않거나 작성자가 본인이 아니면 실패
////	    if (origin == null || origin.getVisitorNo() != loginMember.getMemberNo()) {
////	        return 0;
////	    }
////
////	    // 작성자 번호 세팅 후 수정 수행
////	    inputGuestBook.setVisitorNo(loginMember.getMemberNo());
////	    return service.guestBookUpdate(inputGuestBook);
////	}
////
//	// 삭제
////	@DeleteMapping("{memberNo:[0-9]+}/guestbook")
////	public int deleteGuestBook( @SessionAttribute(value="loginMember",required=false) Member loginMember,
////			@PathVariable("memberNo")int memberNo,
////	        @RequestParam("guestBookNo") int guestBookNo,
////	        @RequestParam(value = "cp", required = false, defaultValue = "1") int cp
////	        ) {
////		
////		GuestBook guestBook = service.selectOne(guestBookNo);
////		int loginMemberNo = loginMember.getMemberNo();
////		
////		
////		
////	    Map<String, Integer> map = new HashMap<>();
////	    map.put("guestBookNo", guestBookNo);
////	    map.put("memberNo", memberNo);
////		
////		return service.guestBookDelete(map);
//		
//		
////		//로그인 안한 경우 차단
////		if(loginMember==null) {
////			return 0;
////		}
////		
////		
////		
////		
////		 // 작성자 or 홈피 주인만 삭제 가능
////	    if (guestBook == null ||
////	        (guestBook.getVisitorNo() != loginMemberNo 
////	        && guestBook.getOwnerNo() != loginMemberNo)) {
////	        return 0;
////	    }
////
//
////		   
//	//}
//	
//	@DeleteMapping("{memberNo:[0-9]+}/guestbook")
//	public int deleteGuestBook(
//	    @SessionAttribute(value="loginMember", required=false) Member loginMember,
//	    @PathVariable("memberNo") int memberNo,
//	    @RequestBody Map<String, Integer> paramMap // ← JSON으로 받기
//	) {
//	    int guestBookNo = paramMap.get("guestBookNo");
//	    
//	    Map<String, Integer> map = new HashMap<>();
//	    map.put("guestBookNo", guestBookNo);
//	    map.put("memberNo", memberNo);
//
//	    return service.guestBookDelete(map);
//	}

//	
	/**
	 * 방명록 페이지 이동 (초기 로딩)
	 * @param memberNo 홈피 주인 번호
	 * @param loginMember 로그인한 회원 정보
	 * @param cp 현재 페이지
	 * @param model 모델
	 * @return 방명록 페이지
	 */
	@GetMapping("{memberNo:[0-9]+}/guestbook")
	public String guestBookPage(@PathVariable("memberNo") int memberNo,
			@SessionAttribute(value = "loginMember", required = false) Member loginMember,
			@RequestParam(value = "cp", required = false, defaultValue = "1") int cp,
			Model model) {
		

		// 방명록 목록과 페이징 정보 조회
		Map<String, Object> result = service.selectGuestBookList(memberNo, cp);
		
		model.addAttribute("guestBookList", result.get("guestBookList"));
		model.addAttribute("pagination", result.get("pagination"));
		model.addAttribute("loginMember", loginMember);
		model.addAttribute("ownerNo", memberNo);

		return "guestBook/guestBook";
	}

	/**
	 * 비동기로 방명록 목록 조회 (페이징 포함)
	 * @param memberNo 홈피 주인 번호
	 * @param cp 현재 페이지
	 * @return 방명록 목록과 페이징 정보
	 */
//	@GetMapping("{memberNo:[0-9]+}/guestbook/list")
//	@ResponseBody
//	public ResponseEntity<Map<String, Object>> getGuestBookList(
//			@PathVariable("memberNo") int memberNo,
//			@RequestParam(value = "cp", defaultValue = "1") int cp) {
//
//		try {
//			Map<String, Object> result = service.selectGuestBookList(memberNo, cp);
//			return ResponseEntity.ok(result);
//		} catch (Exception e) {
//			Map<String, Object> errorMap = new HashMap<>();
//			errorMap.put("success", false);
//			errorMap.put("message", "방명록 목록 조회 중 오류가 발생했습니다.");
//			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorMap);
//		}
//	}

	//responseEntity 아닌버전 을 한번 추가해봄
	@GetMapping("{memberNo:[0-9]+}/guestbook/list")
	@ResponseBody
	public Map<String, Object> getGuestBookList(
	        @PathVariable("memberNo") int memberNo,
	        @RequestParam(value = "cp", defaultValue = "1") int cp) {

		
	    return service.selectGuestBookList(memberNo, cp);
	}


	
	
	
	
	
	
//	
//	@GetMapping("{memberNo:[0-9]+}/guestbook")
//	public String guestBookPage(@PathVariable("memberNo") int memberNo,
//	        @SessionAttribute(value = "loginMember", required = false) Member loginMember,
//	        @RequestParam(value = "cp", required = false, defaultValue = "1") int cp,
//	        Model model) {
//
//	    int loginMemberNo = (loginMember != null) ? loginMember.getMemberNo() : -1;
//	    boolean isOwner = loginMemberNo == memberNo;
//
//	    List<GuestBook> guestBookList = service.selectGuestBookList(memberNo, isOwner);
//
//	    model.addAttribute("guestBookList", guestBookList);
//	    model.addAttribute("loginMember", loginMember);
//	    model.addAttribute("ownerNo", memberNo);
//
//	    return "guestBook/guestBook"; // templates/guestBook/guestBook.html
//	}
//
//	
//	
//	
//	
//	@GetMapping("{memberNo:[0-9]+}/guestbook/list")
//	@ResponseBody
//	public Map<String, Object> selectGuestBookList(
//	        @PathVariable("memberNo") int memberNo,
//	        @RequestParam(value = "cp", defaultValue = "1") int cp,
//	        @SessionAttribute(value = "loginMember", required = false) Member loginMember) {
//
//	    int loginMemberNo = (loginMember != null) ? loginMember.getMemberNo() : -1;
//	    boolean isOwner = loginMemberNo == memberNo;
//
//	    List<GuestBook> guestBookList = service.selectGuestBookList(memberNo, isOwner);
//
//	    Map<String, Object> map = new HashMap<>();
//	    map.put("guestBookList", guestBookList);
//
//	    return map;
//	}
//
//	
//	
//	
//	
	
	
	
	
	
	/**
	 * 방명록 작성 (비동기)
	 * @param loginMember 로그인한 회원
	 * @param guestBook 방명록 정보
	 * @param memberNo 홈피 주인 번호
	 * @return 작성 결과
	 */
	@PostMapping("{memberNo:[0-9]+}/guestbook")
	@ResponseBody
	public ResponseEntity<Map<String, Object>> insertGuestBook(
			@SessionAttribute(value = "loginMember", required = false) Member loginMember,
			@RequestBody GuestBook guestBook,
			@PathVariable("memberNo") int memberNo) {

		Map<String, Object> result = new HashMap<>();

		try {
			// 로그인 체크
			if (loginMember == null) {
				result.put("success", false);
				result.put("message", "로그인이 필요합니다.");
				return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(result);
			}

			// 작성자/홈피 주인 정보 설정
			guestBook.setVisitorNo(loginMember.getMemberNo());
			guestBook.setOwnerNo(memberNo);

			// 방명록 작성
			int insertResult = service.guestBookInsert(guestBook);

			if (insertResult > 0) {
				result.put("success", true);
				result.put("message", "방명록이 작성되었습니다.");
			} else {
				result.put("success", false);
				result.put("message", "방명록 작성에 실패했습니다.");
			}

			return ResponseEntity.ok(result);

		} catch (Exception e) {
			result.put("success", false);
			result.put("message", "방명록 작성 중 오류가 발생했습니다.");
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(result);
		}
	}

	/**
	 * 방명록 수정 (비동기)
	 * @param loginMember 로그인한 회원
	 * @param guestBook 수정할 방명록 정보
	 * @param memberNo 홈피 주인 번호
	 * @return 수정 결과
	 */
	@PutMapping("{memberNo:[0-9]+}/guestbook")
	@ResponseBody
	public int updateGuestBook(
	    @SessionAttribute(value = "loginMember", required = false) Member loginMember,
	    @RequestBody GuestBook guestBook,
	    @PathVariable("memberNo") int memberNo) {

	    // 로그인 안 했을 경우 0 반환
	    if (loginMember == null) return 0;

	    // 작성자 번호 설정 (검증용)
	    guestBook.setVisitorNo(loginMember.getMemberNo());

	    // 서비스로 업데이트 요청
	    return service.guestBookUpdate(guestBook); // 성공 시 1, 실패 시 0
	}


	/**
	 * 방명록 삭제 (비동기)
	 * @param loginMember 로그인한 회원
	 * @param memberNo 홈피 주인 번호
	 * @param requestBody 삭제할 방명록 번호
	 * @return 삭제 결과
	 */
	@DeleteMapping("{memberNo:[0-9]+}/guestbook")
	@ResponseBody
	public ResponseEntity<Map<String, Object>> deleteGuestBook(
			@SessionAttribute(value = "loginMember", required = false) Member loginMember,
			@PathVariable("memberNo") int memberNo,
			@RequestBody Map<String, Integer> requestBody) {

		Map<String, Object> result = new HashMap<>();

		try {
			// 로그인 체크
			if (loginMember == null) {
				result.put("success", false);
				result.put("message", "로그인이 필요합니다.");
				return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(result);
			}

			int guestBookNo = requestBody.get("guestBookNo");
			
//			if (guestBookNo == null) {
//				result.put("success", false);
//				result.put("message", "방명록 번호가 필요합니다.");
//				return ResponseEntity.badRequest().body(result);
//			}

			// 삭제 실행
			int deleteResult = service.guestBookDelete(guestBookNo);

			if (deleteResult > 0) {
				result.put("success", true);
				result.put("message", "방명록이 삭제되었습니다.");
			} else {
				result.put("success", false);
				result.put("message", "방명록 삭제에 실패했습니다.");
			}

			return ResponseEntity.ok(result);

		} catch (Exception e) {
			result.put("success", false);
			result.put("message", "방명록 삭제 중 오류가 발생했습니다.");
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(result);
		}
	}

}
