package com.featherworld.project.miniHome.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;



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
    @GetMapping("{memberNo:[0-9]+}/editprofile")
    public String showEditProfilePage(@PathVariable("memberNo") int memberNo, Model model, HttpSession session) {
        // 로그인 사용자 정보 조회
        Member member = (Member) session.getAttribute("loginMember");
        model.addAttribute("member", member);
        model.addAttribute("isEditMode", true);
        return "profile/editprofile";
    }


    // 프로필 수정 저장 후 프로필 화면으로 이동
    @GetMapping("{memberNo:[0-9]+}/profileupdate")
    public String profileUpdate(@PathVariable("memberNo") int memberNo, Model model) {
        model.addAttribute("memberNo", memberNo);
        return "profile/profileupdate"; // 뷰 이름
    }
}