package com.featherworld.project.member.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.featherworld.project.member.model.dto.Member;
import com.featherworld.project.member.model.service.MemberService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;


/** member 컨트롤러 클래스
 * @author 영민
 */

@SessionAttributes({"loginMember"})
@RequestMapping("member")
@Controller
public class MemberController {

	@Autowired
	private MemberService service;
	
	/** 회원가입 하는 페이지로 이동하는 메서드(get)
	 * @param param
	 * @return
	 * @author 영민
	 */
	@GetMapping("signUp")
	public String signUp() {
		
		return "member/signUp";
	}
		
	/** 회원가입 메서드 (post)
	 * @author 영민
	 * @param inputMember
	 * @param memberAddress
	 * @param ra
	 * @return
	 */
	@PostMapping("signup")
	public String signup(Member inputMember, @RequestParam("memberAddress") String[] memberAddress, RedirectAttributes ra  
			
			                 ) {
	 	
		int result = service.signUp(inputMember,memberAddress); 
		
		String path = null; //경로
		String message = null; // ra 메시지
		
		if(result > 0) {
			
			int memberNo=inputMember.getMemberNo();
			
			int typeResult = service.setDefaultBoardType(memberNo);
			
			if (typeResult > 0) {
				
				message = inputMember.getMemberName()+ "님 회원가입완료";
				path = "/";
				
			}
			
						
		}else {
			
			message = "회원가입실패..";
			path = "signUp";
					
		}
		ra.addAttribute("message", message);
		
		
		return "redirect:" + path;
	}
	
	

	
	
	
	/**로그인 하는 메서드
	 * @return
	 * @author 영민
	 */
	// 아직 미완성~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	@PostMapping("login")
	public String login() {
		
		return null;
	}
}
