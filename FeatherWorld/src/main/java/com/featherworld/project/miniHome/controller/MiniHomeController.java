package com.featherworld.project.miniHome.controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.featherworld.project.member.model.dto.Member;
import com.featherworld.project.miniHome.model.service.FollowService;
import com.featherworld.project.miniHome.model.service.VisitService;

import jakarta.servlet.http.HttpSession;

@Controller
public class MiniHomeController {

    @Autowired
    private FollowService followService;

    @Autowired
    private VisitService visitService;

    @GetMapping("{memberNo:[0-9]+}/minihome")
    public String miniHome(@PathVariable("memberNo") int memberNo, Model model) {
        model.addAttribute("memberNo", memberNo);
        return "miniHome/miniHome";
    }

    @GetMapping("/sidebar")
    public String getSidebar(Model model, HttpSession session) {
        Member loginMember = (Member) session.getAttribute("loginMember");

        if (loginMember != null) {
            int memberNo = loginMember.getMemberNo();  // int 타입으로 받고,
            String memberEmail = loginMember.getMemberEmail();  // getMemberEmail() 호출

            model.addAttribute("member", loginMember);
            model.addAttribute("membername", loginMember.getMemberName());
            model.addAttribute("memberEmail", memberEmail);
            model.addAttribute("followerCount", followService.countFollowers(memberNo));
            model.addAttribute("followingCount", followService.countFollowing(memberNo));
            model.addAttribute("visitorCount", visitService.getTodayTotalCount(memberNo));
        } else {
            // 비로그인 상태 기본값
            model.addAttribute("membername", "Guest");
            model.addAttribute("memberEmail", "");
            model.addAttribute("followerCount", 0);
            model.addAttribute("followingCount", 0);
            model.addAttribute("isLoggedIn", false);
        }


        return "fragments/sidebar :: sidebar";
    }
}
