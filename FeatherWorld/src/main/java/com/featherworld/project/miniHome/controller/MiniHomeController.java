package com.featherworld.project.miniHome.controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.featherworld.project.member.model.dto.Member;
import com.featherworld.project.miniHome.model.service.MiniService;

import jakarta.servlet.http.HttpSession;

@Controller
public class MiniHomeController {

	@Autowired
	private MiniService service;

	private Object loginMemberNo;

	private Object cp;

	@GetMapping("{memberNo:[0-9]+}/minihome")
	public String miniHome(@PathVariable("memberNo") int memberNo, Model model, HttpSession session) {
	    model.addAttribute("member", memberNo);
	    model.addAttribute("memberNo", memberNo);

	    Member loginMember = (Member) session.getAttribute("loginMember"); // 여기서 꺼내서
	    model.addAttribute("loginMember", loginMember);                     // 모델에 다시 넣어줘야 함

	    return "miniHome/miniHome";
	}


	@PostMapping("{memberNo:[0-9]+}/prifleupdate")
	public String fileUpload1(@RequestParam("uploadFile") MultipartFile uploadFile,
								RedirectAttributes ra) 
					throws Exception {
		
		String path = service.fileUpload1(uploadFile);
		
		if( path != null) {
			ra.addFlashAttribute("path", path);
		}
				
		return "profile/profileupdate";
		
	}
	
	/** 업로드한 파일 DB 저장 + 서버 저장 + 조회
	 * @param uploaFile
	 * @return
	 */
	@PostMapping("{memberNo:[0-9]+}/minihome")
	public String fileUpload2(@RequestParam("uploadFile") MultipartFile uploaFile, 
							@SessionAttribute("loginMember") Member loginMember,
							RedirectAttributes ra) throws Exception{
								
		// 로그인한 회원 번호
		int memberNo = loginMember.getMemberNo();
		
		// 업로드된 파일 정보를 db로
		int result = service.fileUpload2(uploaFile, memberNo);
		
		return "redirect:/miniHome/miniHome";
	}
}
