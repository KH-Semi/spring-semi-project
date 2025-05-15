package com.featherworld.project.friend.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.SessionAttribute;

import com.featherworld.project.friend.model.service.IlchonService;
import com.featherworld.project.member.model.dto.Member;

//@Controller
@RestController // @Controller + @ResponseBody
@RequestMapping("friendList")
public class IlchonController {
	
	@Autowired
	private IlchonService service;
	
	@GetMapping("friendList")
	public String select(@SessionAttribute Member loginMember
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
	
}
