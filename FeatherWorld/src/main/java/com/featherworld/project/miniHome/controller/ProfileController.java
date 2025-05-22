package com.featherworld.project.miniHome.controller;

import java.io.File;
import java.io.IOException;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.featherworld.project.member.model.dto.Member;

import jakarta.servlet.http.HttpSession;

@Controller
public class ProfileController {
	
	// 프로필 화면
	 @GetMapping("{memberNo:[0-9]+}/profile")
	    public String miniHome(@PathVariable("memberNo") int memberNo, Model model) {
	    	model.addAttribute("memberNo", memberNo);  // memberNo 추가
	        return "profile/profile";
	    }

 
    // 프로필 수정 화면으로 이동
	 @PostMapping("/{memberNo}/editprofile")
	 public String updateProfile(
	     @PathVariable("memberNo") int memberNo,
	     @RequestParam("bio") String bio,
	     @RequestParam("profileImage") MultipartFile profileImage,
	     HttpSession session,
	     Model model
	 ) {
	     Member member = (Member) session.getAttribute("loginMember");

	     if (member != null && member.getMemberNo() == memberNo) {
	         // 1. 이미지 파일 저장 (예시)
	         if (!profileImage.isEmpty()) {
	             String fileName = profileImage.getOriginalFilename();
	             String uploadDir = "/your/upload/path"; // 실제 저장 경로 설정
	             File dest = new File(uploadDir, fileName);
	             try {
	                 profileImage.transferTo(dest);
	                 member.setMemberImg("/upload/" + fileName); // 웹 경로로 저장
	             } catch (IOException e) {
	                 e.printStackTrace();
	             }
	         }

	         // 2. 소개글 저장
	         member.setMemberIntro(bio);

	         // 3. DB 업데이트 (서비스 호출)
	         memberService.updateProfile(member);  // 이 메서드는 별도로 구현 필요

	         // 4. 세션도 업데이트
	         session.setAttribute("loginMember", member);

	         return "redirect:/" + memberNo + "/profile";
	     }

	     return "redirect:/error";
	 }


    // 프로필 수정 저장 후 프로필 화면으로 이동
	 @GetMapping("/{memberNo:[0-9]+}/profileupdate")
	 public String profileUpdate(@PathVariable int memberNo, Model model) {
	     // memberNo는 URL에서 숫자만 추출된 값으로 들어옴
	     // 예: /123/profileupdate → memberNo = 123
	     // 회원 정보 조회, 모델에 담기, 뷰 반환 등 처리
	     model.addAttribute("member", memberService.findById(memberNo));
	     return "profileUpdateForm";
	 }

}