package com.featherworld.project.member.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.featherworld.project.member.model.dto.Member;
import com.featherworld.project.member.model.service.MemberService;




/** member 컨트롤러 클래스
 * @author 영민
 */

@SessionAttributes({"loginMember"})

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
	

		
	/** 회원가입 메서드 (post)a
	 * @author 영민
	 * @param inputMember
	 * @param memberAddress
	 * @param ra
	 * @return
	 */
	@PostMapping("signup")
	public String signUp(Member inputMember, @RequestParam("memberAddress") String[] memberAddress, RedirectAttributes ra  
			
			                 ) {
	 	
		int result = service.signUp(inputMember,memberAddress); 
		
		String path = null; //경로
		String message = null; // ra 메시지
		
		if(result > 0) {
			
			message = inputMember.getMemberName()+ "님 회원가입완료";
			path = "/";

			}else {			
		 
			message = "회원가입실패..";
			path = "signUp";
					
		     }
		ra.addFlashAttribute("message", message);
		
		
		return "redirect:" + path;
	}
	
	

	
	
	
	/**로그인 하는 메서드
	 * @return
	 * @author 영민
	 */
	// 아이디 저장은 아직 안함
	@PostMapping("login")
	public String login(Member inputMember,RedirectAttributes ra, Model model) {
		
		Member loginMember = service.login(inputMember);
		
		if(loginMember == null) {
			// 로그인실패
			ra.addFlashAttribute("message","아이디또는 비밀번호가 맞지않습니다,");
			
		}else {
			//로그인성공
			model.addAttribute("loginMember",loginMember);
			
			// --- saveId 할꺼면 여기서부터 진행..
		}
		
		
		return "redirect:/";
	}
	
	/** 이메일 중복하는 메서드(비동기..)
	 * @param memberEmail
	 * @return
	 * @author 영민
	 */
	@GetMapping("checkEmail")
	@ResponseBody
	public int checkEmail(@RequestParam("memberEmail") String memberEmail) {
		return service.checkEmail(memberEmail);
	}
	
	/** 로그아웃
	 * @param status
	 * @return
	 * @author 영민
	 */
	@GetMapping("logout")
	public String logout(SessionStatus status) {
		
		status.setComplete();
		return "redirect:/";
	}
	
	/** 아이디 찾기 사이트로 포워드
	 * @return
	 * @author 영민
	 */
	@GetMapping("findId")
	public String findId() {
		return "member/findId";
	}
	
	/** 비밀번호 찾기 로 이동
	 * @return
	 */
	@GetMapping("findPw")
	public String finePw() {
		return "member/findPw";
	}
	
	
	/** 아이디 찾는 서비스
	 * @param memberTel
	 * @param memberEmail
	 * @param ra
	 * @author 영민
	 * @return memberEmail
	 */
	@PostMapping("findId")
	@ResponseBody
	public String findId(Member inputMember,
			             RedirectAttributes ra) {
		
		Member inputMemberEmail = service.findId(inputMember);
		
		if(inputMemberEmail == null) {
		
			return "";
	
		}
		
		
		String getEmail = inputMemberEmail.getMemberEmail();
		
		
		return getEmail;
	}
	
	/**비밀번호 재설정
	 * @param map
	 * @return
	 * @author 영민
	 */
	@PostMapping("/resetPassword")
	@ResponseBody
	public int resetPassword(@RequestBody Map<String, String> map) {
		
		int result = service.resetPassword(map);
		
		if(result >0) {
			
			return result;
		}else {
			
			return 0;
		}
		
	}
		
		
		
	
}
