package com.featherworld.project.miniHome.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.SessionAttribute;

import com.featherworld.project.board.model.dto.Board;
import com.featherworld.project.board.model.dto.Comment;
import com.featherworld.project.friend.model.dto.IlchonComment;
import com.featherworld.project.member.model.dto.Member;
import com.featherworld.project.miniHome.model.dto.MiniHomeRecentBoard;
import com.featherworld.project.miniHome.model.service.MiniHomeService;

@Controller
public class MiniHomeController {

    @Autowired
    private MiniHomeService miniHomeService;

    /**
     * 미니홈 메인 페이지
     * 프로필 데이터와 방문자 처리는 ProfileInterceptor에서 자동 처리됨
     */
    @GetMapping("{memberNo:[0-9]+}/minihome")
    public String miniHomePage(@PathVariable("memberNo") int memberNo,
                              @SessionAttribute(value = "loginMember", required = false) Member loginMember,
                              Model model) {
        
       List<Board> recentBoardList = miniHomeService.getRecentBoards(memberNo);

       List<IlchonComment> ilchonCommets = miniHomeService.getIlchonComments(memberNo);
       
       int totalBoardCount = miniHomeService.getTotalBoardCount(memberNo);
       int totalGuestBookCount = miniHomeService.getTotalGuestBookCount(memberNo);
       
       model.addAttribute("totalBoardCount", totalBoardCount);
       model.addAttribute("totalGuestBookCount", totalGuestBookCount);
       model.addAttribute("recentBoards", recentBoardList);
       model.addAttribute("ilchonCommets",ilchonCommets);
    
    	
        
        return "miniHome/miniHome"; // templates/miniHome/miniHome.html
    }
    
  
}