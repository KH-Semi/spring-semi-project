package com.featherworld.project.miniHome.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;  // 이걸 import 해야 해요
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;

import com.featherworld.project.friend.model.dto.IlchonComment;
import com.featherworld.project.member.model.dto.Member;
import com.featherworld.project.miniHome.model.dto.MiniHomeRecentBoard;
import com.featherworld.project.miniHome.model.service.MiniHomeService;

@Controller
public class MiniHomeController {

    @Autowired
    private MiniHomeService miniHomeService;

    @GetMapping("{memberNo:[0-9]+}/minihome")
    public String miniHome(@PathVariable("memberNo") int memberNo, Model model,
            @ModelAttribute("loginMember") Member loginMember) {

        model.addAttribute("memberNo", memberNo);
        model.addAttribute("member", loginMember);

        // 미니홈 주인 정보 (프로필)
        Member miniHomeOwner = miniHomeService.findmember(memberNo);
        model.addAttribute("miniHomeOwner", miniHomeOwner);

        // 최근 게시글
        List<MiniHomeRecentBoard> recentBoards = miniHomeService.getRecentBoards(memberNo);
        model.addAttribute("recentBoards", recentBoards);

        // 일촌평 리스트
        List<IlchonComment> ilchonComments = miniHomeService.getIlchonComments(memberNo);
        model.addAttribute("ilchonComments", ilchonComments);

        return "minihome/minihome";
    }
}
