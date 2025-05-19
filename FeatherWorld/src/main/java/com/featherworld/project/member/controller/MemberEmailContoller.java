package com.featherworld.project.member.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.featherworld.project.member.model.service.EmailService;

@Controller
@RequestMapping("email")
public class MemberEmailContoller {

	@Autowired
	private EmailService service;
	
	/** 이메일 발송 
	 * @param emailMap
	 * @return
	 * @author 영민
	 */
	@ResponseBody
	@PostMapping("signup")
	public int signUp(@RequestBody Map<String, String> emailMap) {
	    String email = emailMap.get("email"); // JSON으로 받은 이메일 추출
	    
	    String authKey = service.sendEmail("signUp", email);
	    
	    if(authKey != null) {
	        return 1;
	    }
	    
	    return 0;
	}
	
	/** 이메일 이랑 인증번호가 맞는지 확인하는 컨트롤러
	 * @param map
	 * @return
	 * @author 영민
	 */
	@ResponseBody
	@PostMapping("checkAuthKey")
	public int checkAuthKey(@RequestBody Map<String, String> map) {
		return service.checkAuthKey(map);
	}
	
}
