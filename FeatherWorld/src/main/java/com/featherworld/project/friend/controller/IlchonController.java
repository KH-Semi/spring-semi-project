package com.featherworld.project.friend.controller;

import com.featherworld.project.friend.model.service.IlchonService;
import com.featherworld.project.member.model.dto.Member;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttribute;

import java.util.Map;

@Controller
//@RestController // @Controller + @ResponseBody
public class IlchonController {
	
	@Autowired
	private IlchonService service;
	
	@GetMapping("{memberNo:[0-9]+}/friendlist")
	public String select(@SessionAttribute Member loginMember, @PathVariable int memberNo
			,@RequestParam(value = "cp", required = false, defaultValue = "1") int cp
			,Model model){
		//Session에서 loginMember의 MEMBER_NO를 불러오기.
		
		
		// loginMember의 MEMBER_NO를 불러오기
		int loginMemberNo = loginMember.getMemberNo(); 
		
		Map<String, Object> map = service.selectIlchonMemberList(loginMemberNo, cp);
		//map에서 ilchons 따로 변수로 뺴낼것
		
		model.addAttribute("ilchons", map.get("ilchons"));
	    model.addAttribute("pagination", map.get("pagination"));
		return "friendList/friendList";
		
	}
	// 250515 아직 로그인 세션기능이 구현되지 않았으므로 시험할수 있는 controller 내부 함수 구현
	@GetMapping("{memberNo:[0-9]+}/friendList/test")
	public String selectTest(/* @SessionAttribute Member loginMember*/ 
			@RequestParam(value = "cp", required = false, defaultValue = "1") int cp
			,Model model){
		//Session에서 loginMember의 MEMBER_NO를 불러오기.
		
		
		// loginMember의 MEMBER_NO를 불러오는 대신 MEMBER_NO = 1을 시험적으로 불러온다.
		int loginMemberNo = 3;/*loginMember.getMemberNo(); */
		Map<String, Object> map = service.selectIlchonMemberList(loginMemberNo, cp);
		//map에서 ilchons 따로 변수로 뺴낼것
		model.addAttribute("ilchons", map.get("ilchons"));
	    model.addAttribute("pagination", map.get("pagination"));
		return "friendList/friendList";
		
	}
	
}
