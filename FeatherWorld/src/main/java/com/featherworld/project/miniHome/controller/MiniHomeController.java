package com.featherworld.project.miniHome.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;

import com.featherworld.project.member.model.dto.Member;

import jakarta.servlet.http.HttpSession;

@Controller
public class MiniHomeController {

    @GetMapping("{memberNo:[0-9]+}/minihome")
    public String miniHome(@PathVariable("memberNo") int memberNo, Model model) {
    	model.addAttribute("memberNo", memberNo);  // memberNo 추가
        return "miniHome/miniHome";
    }
    
    @GetMapping("/api/member/profile")
    @ResponseBody
    public Member getProfile(HttpSession session) {
        Member loginMember = (Member) session.getAttribute("loginMember");
        if (loginMember == null) {
            return null; // 또는 에러 처리
        }
        // 필요시 서비스에서 추가 정보 조회 후 반환
        return loginMember;
    }
}
