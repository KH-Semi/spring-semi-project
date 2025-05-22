package com.featherworld.project.miniHome.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.featherworld.project.member.model.dto.Member;
import com.featherworld.project.miniHome.model.service.MiniHomeService;

import ch.qos.logback.core.model.Model;
import jakarta.servlet.http.HttpSession;

@Controller
public class MiniHomeController {


	@Autowired
	private MiniHomeService service;

	@GetMapping("{memberNo:[0-9]+}/minihome")
	public String miniHome(@PathVariable("memberNo") int memberNo, Model model, HttpSession session) {
	    
	    Member member = service.selectMemberByNo(memberNo); // 서비스에서 회원 객체 조회
	    model.addAttribute("member", member); // ✅ 이제 member는 객체(Member)

	    model.addAttribute("memberNo", memberNo);

	    Member loginMember = (Member) session.getAttribute("loginMember");
	    model.addAttribute("loginMember", loginMember);

	    return "miniHome/miniHome";
	}

	/** 미니 홈 사진
	 * @param aaaList
	 * @param bbbList
	 * @param memberNo
	 * @param ra
	 * @return
	 * @throws Exception
	 */
	@PostMapping("/{memberNo:[0-9]+}/minihome")
	public String uploadImages(@RequestParam("aaa") List<MultipartFile> aaaList,
	                           @RequestParam("bbb") List<MultipartFile> bbbList,
	                           @PathVariable("memberNo") int memberNo,
	                           RedirectAttributes ra) throws Exception {

	    int totalFileCount = 0;

	    for (MultipartFile file : aaaList) {
	        if (!file.isEmpty()) totalFileCount++;
	    }
	    for (MultipartFile file : bbbList) {
	        if (!file.isEmpty()) totalFileCount++;
	    }

	    if (totalFileCount > 5) {
	        ra.addFlashAttribute("message", "최대 5개의 파일만 업로드할 수 있습니다.");
	        return "redirect:/miniHome/miniHome";
	    }

	    int result = service.fileUpload3(aaaList, bbbList, memberNo);

	    String message = (result == 0) ? "업로드된 파일이 없습니다."
	                                   : result + "개의 파일이 업로드 되었습니다!";
	    ra.addFlashAttribute("message", message);

	    return "redirect:/miniHome/miniHome";
	}

}
