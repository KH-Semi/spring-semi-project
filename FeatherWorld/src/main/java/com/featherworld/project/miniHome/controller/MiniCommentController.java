package com.featherworld.project.miniHome.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.bind.annotation.ResponseBody;

import com.featherworld.project.board.model.dto.Comment;
import com.featherworld.project.friend.model.dto.Ilchon;
import com.featherworld.project.friend.model.service.IlchonService;
import com.featherworld.project.member.model.dto.Member;
import com.featherworld.project.miniHome.model.service.MiniCommentService;

@Controller
@RequestMapping("miniHomeCommentController")
public class MiniCommentController {

    @Autowired
    private MiniCommentService commentService;
    
    @Autowired
    private IlchonService ilchonService;  // IlchonService 주입
    
    /** 댓글 목록 조회 - JSON 반환 */
    @GetMapping("")
    @ResponseBody
    public List<Comment> select(@RequestParam("miniHome") int boardNo) {
        return commentService.select(boardNo);
    }
    
    /** 댓글 삭제 - JSON 반환 */
    @DeleteMapping("")
    @ResponseBody
    public int delete(@RequestBody int commentNo) {
        return commentService.delete(commentNo);
    }
    
    /** 일촌 여부 확인 API - JSON 반환 */
    @GetMapping("/isIlchon")
    @ResponseBody
    public boolean isIlchon(
        @RequestParam("loginMemberNo") int loginMemberNo,
        @RequestParam("targetMemberNo") int targetMemberNo) {
        
        Ilchon ilchon = ilchonService.selectOne(loginMemberNo, targetMemberNo);
        return ilchon != null;
    }
    
    /** 미니홈피 뷰 페이지 */
    @GetMapping("/minihome")
    public String minihome(Model model,
                           @SessionAttribute(name = "loginMember", required = false) Member loginMember,
                           @RequestParam("boardNo") int boardNo,
                           @RequestParam("boardOwnerNo") int boardOwnerNo) {

        boolean isIlchon = false;
        if (loginMember != null) {
            Ilchon ilchon = ilchonService.selectOne(loginMember.getMemberNo(), boardOwnerNo);
            isIlchon = (ilchon != null);
        }

        model.addAttribute("isIlchon", isIlchon);
        model.addAttribute("loginMember", loginMember);
        model.addAttribute("boardNo", boardNo);
        model.addAttribute("boardOwnerNo", boardOwnerNo);

        // 댓글 리스트도 
        model.addAttribute("board", commentService.select(boardNo));

        return "miniHome"; // 뷰 이름 (Thymeleaf 등)
    }
}
