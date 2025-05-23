package com.featherworld.project.miniHome.controller;

import java.io.IOException;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.bind.annotation.ModelAttribute;

import com.featherworld.project.member.model.dto.Member;


@Controller
@RequestMapping("/")
@SessionAttributes("loginMember") // 세션에 loginMember가 존재하는 경우 자동 주입 가능
public class ProfileController {

    // 프로필 화면
    @GetMapping("{memberNo:[0-9]+}/profile")
    public String miniHome(@PathVariable("memberNo") int memberNo,
                           Model model,
                           @ModelAttribute("loginMember") Member loginMember) {
        
        // loginMember가 null인 경우(비로그인 사용자) 처리
        if (loginMember == null || loginMember.getMemberNo() != memberNo) {
            return "redirect:/"; // 또는 접근 제한 페이지
        }

        model.addAttribute("memberNo", memberNo);
        model.addAttribute("member", loginMember);

        return "profile/profile";
    }

    // 프로필 수정 화면
    @GetMapping("{memberNo:[0-9]+}/editprofile")
    public String editProfilePage(@PathVariable("memberNo") int memberNo,
                                  Model model,
                                  @ModelAttribute("loginMember") Member loginMember) {

        if (loginMember == null || loginMember.getMemberNo() != memberNo) {
            return "redirect:/"; // 또는 에러 페이지
        }

        model.addAttribute("memberNo", memberNo);
        model.addAttribute("member", loginMember);
        model.addAttribute("isEditMode", true);

        return "profile/editprofile";
    }

    // 프로필 수정 저장 후 프로필 화면으로 이동
        // 기존 프로필 수정 페이지 GET 요청 핸들러
        @GetMapping("{memberNo:[0-9]+}/profileupdate")
        public String profileUpdate(@PathVariable("memberNo") int memberNo,
                                    Model model,
                                    @ModelAttribute("loginMember") Member loginMember) {

            if (loginMember == null || loginMember.getMemberNo() != memberNo) {
                return "redirect:/";
            }

            model.addAttribute("memberNo", memberNo);
            model.addAttribute("member", loginMember);

            return "profile/profileupdate";
        }

        // 프로필 수정 POST 요청 처리 메서드 추가
        @PostMapping("{memberNo:[0-9]+}/profileupdate")
        public String profileUpdatePost(@PathVariable int memberNo,
                                        @RequestParam("uploadFile") MultipartFile uploadFile,
                                        @RequestParam("bio") String bio,
                                        @ModelAttribute("loginMember") Member loginMember) throws IOException {

            if (loginMember == null || loginMember.getMemberNo() != memberNo) {
                return "redirect:/";
            }

            // 1) 이미지 저장 처리
            if (!uploadFile.isEmpty()) {
                // fileService.saveProfileImage(memberNo, uploadFile);
            }

            // 2) 자기소개 업데이트 처리
            // memberService.updateBio(memberNo, bio);

            // 3) 변경 완료 후 프로필 페이지로 리다이렉트
            return "redirect:/" + memberNo + "/profile";

        }

    }

