package com.featherworld.project.myPage.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;  
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.featherworld.project.member.model.dto.Member;
import com.featherworld.project.myPage.model.service.MyPageService;

import lombok.extern.slf4j.Slf4j;

@SessionAttributes({"loginMember"})
@Controller
@RequestMapping("myPage")
@Slf4j
public class MyPageController {

    @Autowired
    private MyPageService service;

    @GetMapping("info") // /myPage/info
    public String info(@SessionAttribute("loginMember") Member loginMember,
                       Model model) {

        // 주소 분리 처리
        String memberAddress = loginMember.getMemberAddress();
        if (memberAddress != null) {
            String[] arr = memberAddress.split("\\^\\^\\^");
            model.addAttribute("postcode", arr[0]);
            model.addAttribute("address", arr[1]);
            model.addAttribute("detailAddress", arr[2]);
        }

        // 로그인 멤버 기본 정보
        model.addAttribute("memberNo", loginMember.getMemberNo());      
        model.addAttribute("nickname", loginMember.getMemberName());   
        model.addAttribute("memberImg", loginMember.getMemberImg());

        // 일촌 수 가져오기
        int ilchonCount = service.getIlchonCount(loginMember.getMemberNo());
        model.addAttribute("ilchonCount", ilchonCount);

        // 만약 getFollowingCount가 필요하다면 MyPageService에 구현 후 아래 주석 해제
        // int ilchonFollowingCount = service.getFollowingCount(loginMember.getMemberNo());
        // model.addAttribute("ilchonFollowingCount", ilchonFollowingCount);

        return "myPage/myPage-info"; // Thymeleaf 뷰 파일 경로
    }
    
    
    
}
