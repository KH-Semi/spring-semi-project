package com.featherworld.project.friend.controller;

import com.featherworld.project.friend.model.dto.Ilchon;
import com.featherworld.project.friend.model.service.IlchonService;
import com.featherworld.project.member.model.dto.Member;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

import org.springframework.web.bind.annotation.*;


import java.util.HashMap;
import java.util.Map;

@Controller
//@RestController // @Controller + @ResponseBody
public class IlchonController {
	
	@Autowired
	private IlchonService service;


	@GetMapping("{memberNo:[0-9]+}/friendList")
	public String select(@SessionAttribute(name = "loginMember", required = false) Member loginMember,
			@PathVariable("memberNo") int memberNo
			,@RequestParam(value = "cp", required = false, defaultValue = "1") int cp
			,Model model){


		//Session에서 loginMember의 MEMBER_NO를 불러오기.
		
		int loginMemberNo=0;
		// loginMember의 MEMBER_NO를 불러오기
		if(loginMember != null) {loginMemberNo = loginMember.getMemberNo(); }

		
		Map<String, Object> map = service.selectIlchonMemberList(memberNo, cp);
		//map에서 ilchons 따로 변수로 뺴낼것
		
		//friendList page에 전달한 현재 홈피 주인의 member DTO

		model.addAttribute("ilchons", map.get("ilchons"));
		model.addAttribute("memberNo", memberNo);

	    model.addAttribute("pagination", map.get("pagination"));
		return "friendList/friendList";
		
	}
	// 250515 아직 로그인 세션기능이 구현되지 않았으므로 시험할수 있는 controller 내부 함수 구현
	@GetMapping("{memberNo:[0-9]+}/friendList/test")

	public String selectTest(/* @SessionAttribute Member loginMember*/ @PathVariable(value="memberNo") int memberNo,

			@RequestParam(value = "cp", required = false, defaultValue = "1") int cp
			,Model model){
		//Session에서 loginMember의 MEMBER_NO를 불러오기.
		
		
		// loginMember의 MEMBER_NO를 불러오는 대신 MEMBER_NO = 1을 시험적으로 불러온다.
		int loginMemberNo = 2;/*loginMember.getMemberNo(); */
		Map<String, Object> map = service.selectIlchonMemberList(loginMemberNo, cp);
		//map에서 ilchons 따로 변수로 뺴낼것
		model.addAttribute("ilchons", map.get("ilchons"));
		model.addAttribute("memberNo", memberNo);
	    model.addAttribute("pagination", map.get("pagination"));
		return "friendList/friendList";
		
	}
	@GetMapping("{memberNo:[0-9]+}/friendList/incoming")

	public String selectIncoming(@SessionAttribute(name = "loginMember", required = true) Member loginMember,
			@PathVariable("memberNo") int memberNo
			,@RequestParam(value = "cp", required = false, defaultValue = "1") int cp
			,Model model){

		//Session에서 loginMember의 MEMBER_NO를 불러오기.
		
		int loginMemberNo=0;
		// loginMember의 MEMBER_NO를 불러오기
		if(loginMember != null) {loginMemberNo = loginMember.getMemberNo(); }

		
		Map<String, Object> map = service.selectIncomingIlchonMemberList(loginMemberNo, cp);
		//map에서 ilchons 따로 변수로 뺴낼것
		
		//friendList page에 전달한 현재 홈피 주인의 member DTO

		model.addAttribute("ilchons", map.get("ilchons"));
		model.addAttribute("ilchonsIncomingCount", map.size()); // 일촌신청(incoming) count 개수
		model.addAttribute("memberNo", memberNo);

	    model.addAttribute("pagination", map.get("pagination"));
		return "friendList/incomingFriendList";
		
	}
	
	@PostMapping("update/nickname")
	@ResponseBody
	public Map<String, Object> updateIlchonNickname(@SessionAttribute(name = "loginMember", required = false) Member loginMember,
			@RequestBody Map<String, String> payload /*클라이언트쪽에서 건너오는 요청*/
			, Model model) {
		String memberNoStr = 	payload.get("memberNo");

		int memberNo =  Integer.parseInt(memberNoStr); // 1. loginMember의 일촌의 memberId 값

		int loginMemberNo=0;
		if(loginMember != null) {loginMemberNo = loginMember.getMemberNo(); }// 2. loginMember본인의 memberId 값

		String nickname = payload.get("nickname");
		System.out.println("loginMember: "+loginMemberNo + ",memberNo: "+ memberNo +",nickname:"+ nickname);
		int result = service.updateIlchonNickname(loginMemberNo,memberNo,nickname);

		if(result == 2) { // 수정 성공시
			Ilchon ilchon = service.selectOne(loginMemberNo, memberNo);

		    //model.addAttribute("pagination", map.get("pagination"));
			Map<String, Object> response = new HashMap<>();
			response.put("Ilchon", ilchon);
			response.put("status", 2);//TO_NICKNAME update success
			return response;

		}
		else if(result == 1) { // 수정 성공시
			Ilchon ilchon = service.selectOne(loginMemberNo, memberNo);
			Map<String, Object> response = new HashMap<>();
			response.put("Ilchon", ilchon);
			response.put("status", 1);//FROM_NICKNAME update success
		    //model.addAttribute("pagination", map.get("pagination"));
			return response;

		}else {//수정 실패시
			Map<String, Object> response = new HashMap<>();

			response.put("status", 0);//NICKNAME update failure
			return response;

		}//홍길동, 김철수

	}

	@PostMapping("/update/accept")
	@ResponseBody
	public Map<String, Object> updateIsIlchon(@SessionAttribute(name = "loginMember", required = false) Member loginMember,
			@RequestBody Map<String, String> payload /*클라이언트쪽에서 건너오는 요청*/
			, Model model) {
		String memberNoStr = 	payload.get("memberNo");

		int memberNo =  Integer.parseInt(memberNoStr); // 1. loginMember의 일촌의 memberId 값

		int loginMemberNo=0;
		if(loginMember != null) {loginMemberNo = loginMember.getMemberNo(); }// 2. loginMember본인의 memberId 값

		String nickname = payload.get("nickname");
		System.out.println("loginMember: "+loginMemberNo + ",memberNo: "+ memberNo +",nickname:"+ nickname);
		/*int result = service.updateIlchonNickname(loginMemberNo,memberNo,nickname);*/
		int result = service.updateIlchonRow(loginMemberNo,memberNo,nickname);
		if(result > 0) {// 수정 성공시 is_ilchon = 'Y'로 업데이트
			
			
		} 
		if(result == 2) { // 수정 성공시
			Ilchon ilchon = service.selectOne(loginMemberNo, memberNo);

		    //model.addAttribute("pagination", map.get("pagination"));
			Map<String, Object> response = new HashMap<>();
			response.put("Ilchon", ilchon);
			response.put("status", 2);//TO_NICKNAME update success
			return response;

		}
		else if(result == 1) { // 수정 성공시
			Ilchon ilchon = service.selectOne(loginMemberNo, memberNo);
			Map<String, Object> response = new HashMap<>();
			response.put("Ilchon", ilchon);
			response.put("status", 1);//FROM_NICKNAME update success
		    //model.addAttribute("pagination", map.get("pagination"));
			return response;

		}else {//수정 실패시
			Map<String, Object> response = new HashMap<>();

			response.put("status", 0);//NICKNAME update failure
			return response;

		}//홍길동, 김철수

	}

	@GetMapping("{memberNo:[0-9]+}/newFriend/input")
	public String inputNewIlchonReq(@PathVariable("memberNo") int memberNo /*클라이언트쪽에서 건너오는 요청*/,
			Model model) {
		model.addAttribute("memberNo", memberNo);
		return "friendList/sendFriendReq";
	}

	//@PostMapping("insert/newFriend")
	//@ResponseBody // 비동기? 동기?
	@PostMapping("insert/newFriend")
	public String insertNewIlchon(@SessionAttribute(name = "loginMember", required = false) Member loginMember,

			/*클라이언트쪽에서 건너오는 요청*//*@RequestBody Map<String, String> payload*/ 
			@RequestParam("memberNo") int memberNo, @RequestParam("nickname") String nickname, Model model) {
		
		
		/*String memberNoStr = 	payload.get("memberNo");
		
		int memberNo =  Integer.parseInt(memberNoStr); */

		int loginMemberNo = loginMember.getMemberNo();
		int result = service.insertNewIlchon(loginMemberNo,memberNo, nickname);
		if(result == 1) {
			return "redirect:/" + memberNo + "/friendList";  //pagination 보존 안함
		}else if(result == 0)  {
			return "redirect:/" + memberNo + "/friendList"; //pagination 보존 안함

		}else if(result == -1) {
			return "redirect:/" + memberNo + "/friendList";  //pagination 보존 안함
		}
		return "redirect:/" + memberNo + "/friendList"; //pagination 보존 안함

	}
	
	@PostMapping("delete")/*작성중*/
	@ResponseBody
	public Map<String, Object> deleteIlchon(@SessionAttribute(name = "loginMember", required = true) Member loginMember,
			@RequestBody Map<String, String> payload /*클라이언트쪽에서 건너오는 요청*/
			, Model model) {
		String memberNoStr = 	payload.get("memberNo");

		int memberNo =  Integer.parseInt(memberNoStr); // 1. loginMember의 일촌의 memberId 값

		int loginMemberNo=0;
		if(loginMember != null) {loginMemberNo = loginMember.getMemberNo(); }// 2. loginMember본인의 memberId 값

		String nickname = payload.get("nickname");
		System.out.println("loginMember: "+loginMemberNo + ",memberNo: "+ memberNo +",nickname:"+ nickname);
		int result = service.deleteIlchon(loginMemberNo,memberNo);

		if(result == 1) { // 수정 성공시
			Ilchon ilchon = service.selectOne(loginMemberNo, memberNo);

		    //model.addAttribute("pagination", map.get("pagination"));
			Map<String, Object> response = new HashMap<>();
			response.put("deleted", ilchon);
			response.put("status", 1);//TO_NICKNAME update success
			return response;

		}
		else if(result == 0) { // 수정 실패시
			Ilchon ilchon = service.selectOne(loginMemberNo, memberNo);
			Map<String, Object> response = new HashMap<>();
			response.put("Ilchon", ilchon);
			response.put("status", 0);//FROM_NICKNAME update success
		    //model.addAttribute("pagination", map.get("pagination"));
			return response;

		}else {//수정 실패시
			Map<String, Object> response = new HashMap<>();

			response.put("status", -1);//NICKNAME update failure
			return response;

		}//홍길동, 김철수

	}
}
