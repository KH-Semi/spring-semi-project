package com.featherworld.project.miniHome.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttribute;

import com.featherworld.project.friend.model.dto.Ilchon;
import com.featherworld.project.member.model.dto.Member;
import com.featherworld.project.member.model.dto.Today;
import com.featherworld.project.miniHome.model.service.MiniHomeService;



@Controller
public class MiniHomeController {

    @Autowired
    private MiniHomeService miniHomeService;
       
    
    @GetMapping("{memberNo:[0-9]+}/minihome")
    public String miniHome(@PathVariable("memberNo") int memberNo ,
                           @SessionAttribute(value ="loginMember", required=false) Member loginMember ,
                           Model model) {
        
       Member member = miniHomeService.findmember(memberNo);
       
       // 방문자 처리 (로그인한 회원만)
       if (loginMember != null && loginMember.getMemberNo() != memberNo) {
           Today today = new Today(); 
           today.setHomeNo(memberNo);
           today.setVisitNo(loginMember.getMemberNo());
           
           // 오늘 이 미니홈에 방문했는지 확인
           int todayConfirm = miniHomeService.todayConfirm(today);
           
           if (todayConfirm == 0) {
               // 오늘 첫 방문이면 방문 기록 추가
               miniHomeService.todayAdd(today);
           }
       }
       
       // 총 오늘 방문자 수 조회
       Today todayQuery = new Today();
       todayQuery.setHomeNo(memberNo);
       int todayCount = miniHomeService.todayCount(todayQuery);
       
       // 전체 방문자 수 조회 
       int totalCount = miniHomeService.totalCount(memberNo);
       
       // 팔로워 수 조회 (남이 나를 일촌신청한 수)
       int followerCount = miniHomeService.getFollowerCount(memberNo);
       
       // 팔로잉 수 조회 (내가 남에게 일촌신청한 수)
       int followingCount = miniHomeService.getFollowingCount(memberNo);
       
       // 미수락 팔로워 신청 수 조회 (본인인 경우만)
       boolean hasPendingFollowers = false; // 초기값 
      
       if (loginMember != null && loginMember.getMemberNo() == memberNo) {
         
    	   int pendingFollowerCount = miniHomeService.getPendingFollowerCount(memberNo);
            hasPendingFollowers = (pendingFollowerCount > 0);
          
       }
       
       model.addAttribute("totalCount", totalCount);    // 총방문자
       model.addAttribute("todayCount", todayCount);	// 투데이
       model.addAttribute("member", member);            // 홈피주인의 대한정보
       model.addAttribute("followerCount", followerCount);   // 팔로워 몇명?
       model.addAttribute("followingCount", followingCount); // 팔로잉 몇명 ?
       model.addAttribute("hasPendingFollowers", hasPendingFollowers); // 미수락된 팔로워 추가해봄
      
       //일촌 관계 초기값
       boolean isIlchon = false;
       // 아 .. 수락된 일촌인지 또 확인해야돼 ㅡㅡ 
       boolean isPendingRequest = false;
       
       if(loginMember != null && loginMember.getMemberNo() != memberNo) {
           // 1. 내가 신청한 상태 확인
           Ilchon myRequest = new Ilchon();
           myRequest.setFromMemberNo(loginMember.getMemberNo());
           myRequest.setToMemberNo(memberNo);
           
           int myAcceptedCount = miniHomeService.findAcceptedIlchon(myRequest);  // 수락된 일촌만
           int myPendingCount = miniHomeService.findPendingIlchon(myRequest);    // 대기중인 신청만
           
           // 2. 상대방이 신청한 상태 확인
           Ilchon theirRequest = new Ilchon();
           theirRequest.setFromMemberNo(memberNo);
           theirRequest.setToMemberNo(loginMember.getMemberNo());
           
           int theirAcceptedCount = miniHomeService.findAcceptedIlchon(theirRequest);
           
           // 3. 상태 결정
           isIlchon = (myAcceptedCount > 0 || theirAcceptedCount > 0);  // 수락된 일촌이 있으면 일촌
           isPendingRequest = (myPendingCount > 0);  // 내가 신청한 상태
           
           model.addAttribute("isIlchon", isIlchon);
           model.addAttribute("isPendingRequest", isPendingRequest);
       }
       
       return "miniHome/miniHome";
   }
    
    /** 일촌신청 
     * @param toMemberNo
     * @param loginMember
     * @return
     */
    @PostMapping("/follow")
    @ResponseBody
    public Map<String, Object> sendFollowRequest(@RequestParam("toMemberNo") int toMemberNo,
                                               @SessionAttribute("loginMember") Member loginMember) {
        
        Map<String, Object> response = new HashMap<>();
        
        // 본인에게 일촌 신청하는 경우 방지
        if (loginMember.getMemberNo() == toMemberNo) {
            response.put("success", false);
            response.put("message", "본인에게는 일촌 신청을 할 수 없습니다.");
            return response;
        }
        
        // 내가 이미 신청했는지 확인
        Ilchon myRequest = new Ilchon();
        myRequest.setFromMemberNo(loginMember.getMemberNo()); // 내가
        myRequest.setToMemberNo(toMemberNo);                // 그사람에게 신청했는지 ?
        
        int myRequestCount = miniHomeService.findilchon(myRequest);
        if (myRequestCount > 0) {
            response.put("success", false);
            response.put("message", "이미 일촌 신청을 보냈거나 일촌 관계입니다.");
            return response;
        }
        
        // 상대방이 나에게 신청했는지 확인
        Ilchon theirRequest = new Ilchon();
        theirRequest.setFromMemberNo(toMemberNo);  //상대방이
        theirRequest.setToMemberNo(loginMember.getMemberNo()); // 나에게 신청햇는지 ?
        
        int theirRequestCount = miniHomeService.findPendingIlchon(theirRequest);
        if (theirRequestCount > 0) {
            response.put("success", false);
            response.put("message", "상대방이 이미 일촌 신청을 보냈습니다. 일촌 신청 목록을 확인해주세요.");
            return response;
        }
        
        // 위 조건들이 만족하면 일촌 신청 진행         
        Ilchon followRequest = new Ilchon();
        followRequest.setFromMemberNo(loginMember.getMemberNo());
        followRequest.setToMemberNo(toMemberNo);
        followRequest.setFromNickname(loginMember.getMemberName());
        
        Member toMember = miniHomeService.findmember(toMemberNo);
        followRequest.setToNickname(toMember.getMemberName());
        
        // 일촌 신청 저장
        int result = miniHomeService.sendFollowRequest(followRequest);
        
        if (result > 0) {
            response.put("success", true);
            response.put("message", toMember.getMemberName() + "님에게 일촌 신청을 보냈습니다!");
        } else {
            response.put("success", false);
            response.put("message", "일촌 신청에 실패했습니다. 다시 시도해주세요.");
        }
        
        return response;
    }
    
    
    
  
}