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
import com.featherworld.project.miniHome.model.service.MiniHomeService;

import jakarta.servlet.http.HttpSession;

@Controller
public class MiniHomeController {

    @Autowired
    private MiniHomeService service;

    @GetMapping("{memberNo:[0-9]+}/minihome")
    public String miniHome(@PathVariable("memberNo") int memberNo, Model model, HttpSession session) {
        
        // 기본 데이터 설정
        model.addAttribute("memberNo", memberNo);
        
        Member loginMember = (Member) session.getAttribute("loginMember");
        model.addAttribute("loginMember", loginMember);
        
        // Fragment에서 필요한 데이터들 추가
        // TODO: 실제로는 Service에서 회원 정보를 조회해서 설정해야 함
        
        // 임시 데이터 (나중에 실제 데이터로 교체)
        if (loginMember != null && loginMember.getMemberNo() == memberNo) {
            // 내 프로필인 경우
            model.addAttribute("membername", loginMember.getMemberName());
            model.addAttribute("memberEmail", loginMember.getMemberEmail());
            model.addAttribute("memberIntro", "안녕하세요! " + loginMember.getMemberName() + "입니다.");
        } else {
            // 다른 사용자 프로필인 경우 - 실제로는 DB에서 조회
            model.addAttribute("membername", "사용자" + memberNo);
            model.addAttribute("memberEmail", "user" + memberNo + "@example.com");
            model.addAttribute("memberIntro", "안녕하세요!");
        }
        
        // 팔로워/팔로잉 수 (임시 데이터)
        model.addAttribute("followerCount", 48);
        model.addAttribute("followingCount", 48);
        
        // 방문자 수 (임시 데이터)
        model.addAttribute("vistorno", "10 / 50");
        
        return "miniHome/miniHome";
    }

    @PostMapping("{memberNo:[0-9]+}/prifleupdate")
    public String fileUpload1(@RequestParam("uploadFile") MultipartFile uploadFile,
            RedirectAttributes ra) throws Exception {

        String path = service.fileUpload1(uploadFile);

        if (path != null) {
            ra.addFlashAttribute("path", path);
        }

        return "profile/profileupdate";
    }

    /** 업로드한 파일 DB 저장 + 서버 저장 + 조회
     * @param uploadFile
     * @return
     */
    @PostMapping("{memberNo:[0-9]+}/minihome")
    public String fileUpload2(@RequestParam("uploadFile") MultipartFile uploadFile,
            @SessionAttribute("loginMember") Member loginMember,
            @PathVariable("memberNo") int memberNo,
            RedirectAttributes ra) throws Exception {

        // 로그인한 회원 번호
        int loginMemberNo = loginMember.getMemberNo();

        // 업로드된 파일 정보를 db로
        int result = service.fileUpload2(uploadFile, loginMemberNo);

        return "redirect:/" + memberNo + "/minihome";
    }
}