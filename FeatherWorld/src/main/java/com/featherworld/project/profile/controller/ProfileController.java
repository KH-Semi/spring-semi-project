package com.featherworld.project.profile.controller;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.featherworld.project.member.model.dto.Member;
import com.featherworld.project.profile.model.dto.Profile;

import com.featherworld.project.profile.model.service.ProfileService;

import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@SessionAttributes({ "loginMember" })
public class ProfileController {

	@Autowired
	private ProfileService profileService;

	// 프로필 보기
	@GetMapping("{memberNo:[0-9]+}/profile")
	public String Profile(@PathVariable("memberNo") int memberNo, Model model) {
		Profile profile = profileService.selectProfile(memberNo);
		log.debug("profile {}", profile);
		model.addAttribute("profile", profile);
		model.addAttribute("memberNo", memberNo); // memberNo도 추가
		return "profile/profile";
	}

	@GetMapping("{memberNo:[0-9]+}/editprofile")
	public String editProfilePage(@PathVariable("memberNo") int memberNo, Model model) {
		Profile profile = profileService.selectProfile(memberNo);
		model.addAttribute("profile", profile);
		model.addAttribute("memberNo", memberNo); // 여기를 추가!
		return "profile/editprofile";
	}

	/**
	 * 프로필 이미지 수정 페이지로 이동
	 * 
	 * @param memberNo
	 * @param model
	 * @param loginMember
	 * @param ra
	 * @return
	 */
	@GetMapping("{memberNo:[0-9]+}/profileupdate")
	public String showProfileUpdateForm(@PathVariable("memberNo") int memberNo, Model model,
			@SessionAttribute("loginMember") Member loginMember, RedirectAttributes ra) {
		if (loginMember.getMemberNo() == memberNo) {
			Profile profile = profileService.selectProfile(memberNo);
			model.addAttribute("profile", profile);
			return "profile/profileupdate";
		} else {
			ra.addFlashAttribute("message", "접근 할 수 없는 없는 경로입니다!(다른사람 프로필 업데이트 불가)");
			return "redirect:/";
		}
	}

	/**
	 * 프로필 사진 업데이트
	 * 
	 * @param memberNo
	 * @param uploadFile
	 * @param bio
	 * @return
	 * @throws IOException
	 */
	@PostMapping("{memberNo:[0-9]+}/profileupdate")
	public String updateProfile(@PathVariable("memberNo") int memberNo,
			@SessionAttribute("loginMember") Member loginMember, @RequestParam("uploadFile") MultipartFile uploadFile,
			@RequestParam("bio") String bio, RedirectAttributes ra)  throws Exception {

		if (loginMember.getMemberNo() == memberNo) {


//			if (profile == null) {
//	            profile = Profile.builder().memberNo(memberNo).build();
//	        }
			// 현재 로그인한 회원의 프로필을 업데이트 할때
			// DB 저장 또는 업데이트
			int result = profileService.saveOrUpdateProfile(loginMember.getMemberNo(), uploadFile, bio);

			String message = null;
			if (result > 0)
				message = "변경 성공!";
			else
				message = "변경 실패";

			ra.addFlashAttribute("message", message);
			return "redirect:/" + memberNo + "/profile";

		} else {
			ra.addFlashAttribute("message", "접근 할 수 없는 없는 경로입니다!(다른사람 프로필 업데이트 불가)");
			return "redirect:/";
		}

	}

	
	/** 회원 탈퇴
	 * @param memberNo
	 * @param model
	 * @return
	 */
	// GET 요청
	@GetMapping("{memberNo}/profiledelete")
	public String profileDelete(@PathVariable("memberNo") int memberNo, HttpSession session, RedirectAttributes ra, Model model) {
		Member loginMember = (Member) session.getAttribute("loginMember");

		if (loginMember == null || loginMember.getMemberNo() != memberNo) {
			ra.addFlashAttribute("message", "잘못된 접근입니다.");
			return "redirect:/";
		}

		model.addAttribute("memberNo", memberNo);
		return "profile/profiledelete"; // profiledelete.html
	}

	// POST 요청
	@PostMapping("{memberNo}/profiledelete")
	public String secession(@PathVariable("memberNo") int memberNo,
	                        @RequestParam("memberPw") String memberPw,
	                        HttpSession session,
	                        RedirectAttributes ra,
	                        SessionStatus status) {

		Member loginMember = (Member) session.getAttribute("loginMember");

		if (loginMember == null || loginMember.getMemberNo() != memberNo) {
			ra.addFlashAttribute("message", "잘못된 접근입니다.");
			return "redirect:/" + memberNo + "/profiledelete";
		}

		int result = profileService.secession(memberPw, memberNo);

		String message;
		String path;

		if (result > 0) {
			message = "탈퇴 되었습니다.";
			path = "/";
			status.setComplete(); // 세션 종료
			session.invalidate(); // 세션 전체 무효화
		} else {
			message = "비밀번호가 일치하지 않습니다.";
			path = "/" + memberNo + "/profiledelete";
		}

		ra.addFlashAttribute("message", message);
		return "redirect:" + path;
	}

}