package com.featherworld.project.member.controller;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.featherworld.project.member.model.dto.Today;
import com.featherworld.project.member.model.dto.Member;
import com.featherworld.project.member.model.service.MemberService;
import com.featherworld.project.miniHome.model.service.MiniHomeService;

import jakarta.servlet.http.HttpSession;

@Controller
public class UpdateMemberController {

    @Autowired
    private MemberService service;
    
    @Autowired
    private MiniHomeService miniHomeService;
    
    /** 회원 이미지 삭제 (x)
     * @param memberNo 현재 회원 번호
     * @author Jiho
     * @return 삭제 성공(OK) / 삭제 실패
     */
    @ResponseBody
    @DeleteMapping("{memberNo:[0-9]+}/memberImage/delete")
    public ResponseEntity<String> deleteMember(@PathVariable("memberNo") int memberNo,
                                               @SessionAttribute("loginMember") Member loginMember) {
        
        try {
            int result = service.deleteProfileImage(memberNo);
            
            if(result == 0) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body("유효하지 않은 회원 번호 : " + memberNo);
                
            } else {
                loginMember.setMemberImg(null);
                
                return ResponseEntity.status(HttpStatus.OK)
                        .body(memberNo + "번 회원 이미지 삭제 성공");
            }
            
        } catch(Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("회원 이미지 삭제 중 오류 발생 : " + e.getMessage());
        }
    }
    
    // GET: 비밀번호 인증 페이지 표시
    @GetMapping("/{memberNo}/updateMember")
    public String updateMemberAuth(@PathVariable("memberNo") int memberNo,
                                  Model model,
                                  @SessionAttribute("loginMember") Member loginMember,
                                  RedirectAttributes ra) {

        // 1. 권한 체크
        if(loginMember.getMemberNo() != memberNo) {
            ra.addFlashAttribute("message", "권한이 없습니다.");
            return "redirect:/";
        }

        // 2. 카카오 로그인 체크  
        if(loginMember.getMemberPw() == null || loginMember.getMemberPw().isEmpty()) {
            ra.addFlashAttribute("message", "카카오로그인은 정보수정이 불가능합니다.");
            return "redirect:/";
        }
        addSidebarData(memberNo, loginMember, model);
        // 3. 정상 진입
        model.addAttribute("memberNo", memberNo);
        return "member/updateMember";
    }

    // POST: 비밀번호 인증 (비동기)
    @PostMapping("/{memberNo}/validatePassword")
    @ResponseBody
    public Map<String, Object> validatePassword(@PathVariable("memberNo") int memberNo,
                                               @RequestBody Map<String, String> requestBody,
                                               @SessionAttribute("loginMember") Member loginMember) {

        System.out.println("=== 비밀번호 검증 시작 ===");
        System.out.println("memberNo: " + memberNo);
        System.out.println("loginMember.memberNo: " + loginMember.getMemberNo());
        
        Map<String, Object> response = new HashMap<>();
        String password = requestBody.get("password");
        
        System.out.println("입력된 비밀번호: " + password);
        System.out.println("로그인 멤버 이메일: " + loginMember.getMemberEmail());

        if(loginMember.getMemberNo() != memberNo) {
            System.out.println("권한 체크 실패");
            response.put("success", false);
            response.put("message", "권한이 없습니다.");
            return response;
        }

        boolean isValid = service.validatePassword(loginMember.getMemberEmail(), password);
        System.out.println("비밀번호 검증 결과: " + isValid);
        
        response.put("success", isValid);
        response.put("message", isValid ? "인증 성공" : "비밀번호가 맞지 않습니다.");

        System.out.println("최종 응답: " + response);
        return response;
    }

    // POST: 회원정보 수정 처리
    @PostMapping("/{memberNo}/updateMember")
    public String updateMemberProcess(@PathVariable("memberNo") int memberNo,
                                     Member inputMember,
                                     @SessionAttribute("loginMember") Member loginMember,
                                     RedirectAttributes ra,
                                     SessionStatus status , HttpSession session) {
        
        if(loginMember.getMemberNo() != memberNo) {
            ra.addFlashAttribute("message", "권한이 없습니다.");
            return "redirect:/";
        }

        // 회원정보 수정
        inputMember.setMemberNo(memberNo);
        int result = service.updateMember(inputMember);
        
        if(result > 0) {
            // 세션 무효화 (다시 로그인하게 만들기)
        	session.invalidate();
            status.setComplete();
            ra.addFlashAttribute("message", "회원정보가 수정되었습니다. 다시 로그인해주세요.");
        } else {
            ra.addFlashAttribute("message", "수정에 실패했습니다.");
        }
        
        	
        return "redirect:/"; // 메인페이지로
    }
    
    
	/** 왼쪽 프로필의 대한 값들의 타임리프값들을 유지시킬 장치
	 * 그냥 필요한곳에다가는 냅다 박아넣어서 써야될것같음
	 * @param memberNo
		 * @param loginMember
		 * @param model
		*/
		private void addSidebarData(int memberNo, Member loginMember, Model model) {
 
  		// 본인기준이긴함 
       // 회원 정보 조회
       Member member = miniHomeService.findmember(memberNo);
       
       // 방문자 수 조회
       Today todayQuery = new Today();
       todayQuery.setHomeNo(memberNo);
       int todayCount = miniHomeService.todayCount(todayQuery);
       int totalCount = miniHomeService.totalCount(memberNo);
       
       // 팔로워/팔로잉 수 조회
       int followerCount = miniHomeService.getFollowerCount(memberNo);
       int followingCount = miniHomeService.getFollowingCount(memberNo);
       
       // 미수락 팔로워 신청 수 조회 (본인인 경우만)
       boolean hasPendingFollowers = false;
       if (loginMember != null && loginMember.getMemberNo() == memberNo) {
           int pendingFollowerCount = miniHomeService.getPendingFollowerCount(memberNo);
           hasPendingFollowers = (pendingFollowerCount > 0);
       }
       
       // 모델에 데이터 추가
       model.addAttribute("member", member);
       model.addAttribute("todayCount", todayCount);
       model.addAttribute("totalCount", totalCount);
       model.addAttribute("followerCount", followerCount);
       model.addAttribute("followingCount", followingCount);
       model.addAttribute("hasPendingFollowers", hasPendingFollowers);
       model.addAttribute("isIlchon", false); // 본인 페이지이므로 false
 
       }

}