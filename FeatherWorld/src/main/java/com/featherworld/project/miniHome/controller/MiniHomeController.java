package com.featherworld.project.miniHome.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttribute;

import com.featherworld.project.board.model.dto.Board;
import com.featherworld.project.friend.model.dto.Ilchon;
import com.featherworld.project.friend.model.dto.IlchonComment;
import com.featherworld.project.member.model.dto.Member;
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

      
       
      // List<IlchonComment> ilchonCommets = miniHomeService.getIlchonComments(memberNo);
       
       int totalBoardCount = miniHomeService.getTotalBoardCount(memberNo);
       int totalGuestBookCount = miniHomeService.getTotalGuestBookCount(memberNo);
       
       model.addAttribute("totalBoardCount", totalBoardCount);
       model.addAttribute("totalGuestBookCount", totalGuestBookCount);
       model.addAttribute("recentBoards", recentBoardList);
     //  model.addAttribute("ilchonCommets",ilchonCommets);
       model.addAttribute("loginMember", loginMember);
      
        
        return "miniHome/miniHome"; // templates/miniHome/miniHome.html
    }
    
    /** 일촌평 작성 ..
     * @param memberNo
     * @param requestData
     * @param loginMember
     * @return
     */
    @PostMapping("{memberNo:[0-9]+}/ilchoncomment")
    @ResponseBody
    public Map<String, Object> createOrUpdateIlchonComment(
            @PathVariable("memberNo") int memberNo,
            @RequestBody Map<String, Object> requestData,
            @SessionAttribute("loginMember") Member loginMember) {

        Map<String, Object> response = new HashMap<>();

        try {
            if (loginMember == null) {
                response.put("success", false);
                response.put("message", "로그인해야됌");
                return response;
            }

            int toMemberNo = (Integer) requestData.get("toMemberNo");
            String commentContent = (String) requestData.get("ilchonCommentContent");

            // URL 검증
            if (memberNo != toMemberNo) {
                response.put("success", false);
                response.put("message", "잘못된 요청입니다.");
                return response;
            }

            // 넘어온 일촌평의 값이 아무것도 없을때 처리
            if (commentContent == null || commentContent.trim().isEmpty()) {
                response.put("success", false);
                response.put("message", "일촌평 내용을 입력해주세요.");
                return response;
            }

            // 30자까지만
            if (commentContent.length() > 30) {
                response.put("success", false);
                response.put("message", "일촌평은 30자 이내로 작성해주세요.");
                return response;
            }

            // 권한 확인
            boolean canWrite = checkWritePermission(loginMember, memberNo);

            if (!canWrite) {
                response.put("success", false);
                response.put("message", "일촌평 작성 권한이 없습니다.");
                return response;
            }

            // 기존 일촌평 확인
            IlchonComment checkComment = new IlchonComment();
            checkComment.setFromMemberNo(loginMember.getMemberNo());
            checkComment.setToMemberNo(memberNo);

            int existingCount = miniHomeService.checkExistingIlchonComment(checkComment);

            // 일촌평 객체 생성
            IlchonComment ilchonComment = new IlchonComment();
            ilchonComment.setFromMemberNo(loginMember.getMemberNo());
            ilchonComment.setToMemberNo(memberNo);
            ilchonComment.setIlchonCommentContent(commentContent.trim());

            int result = 0;
            String message = "";

            if (existingCount > 0) {
                // 기존 일촌평이 있으면 업데이트
                result = miniHomeService.updateIlchonComment(ilchonComment);
                message = result > 0 ? "일촌평이 수정되었습니다." : "일촌평 수정에 실패했습니다.";
            } else {
                // 기존 일촌평이 없으면 신규 작성
                result = miniHomeService.insertIlchonComment(ilchonComment);
                message = result > 0 ? "일촌평이 작성되었습니다." : "일촌평 작성에 실패했습니다.";
            }

            if (result > 0) {
                response.put("success", true);
                response.put("message", message);
            } else {
                response.put("success", false);
                response.put("message", message);
            }

        } catch (Exception e) {
            response.put("success", false);
            response.put("message", "일촌평 처리 중 오류가 발생했습니다.");
            System.err.println("일촌평 작성/수정 오류: " + e.getMessage());
            e.printStackTrace();
        }

        return response;
    }
    	
    /** 
     * 일촌평 삭제 .. RestApi를 써보자잇 
     */
    @DeleteMapping("{memberNo:[0-9]+}/ilchoncomment")
    @ResponseBody
    public Map<String, Object> deleteIlchonComment(
            @PathVariable("memberNo") int memberNo,
            @RequestBody Map<String, Object> requestData,
            @SessionAttribute("loginMember") Member loginMember) {
        
        Map<String, Object> response = new HashMap<>();
        
        
            // 로그인 체크
            if (loginMember == null) {
                response.put("success", false);
                response.put("message", "로그인이 필요합니다.");
                return response;
            }
            
            // 요청 데이터 추출
            int fromMemberNo = (Integer)requestData.get("fromMemberNo"); 
            int toMemberNo = (Integer)requestData.get("toMemberNo");
            
            // URL 일관성 검증
            if (memberNo != toMemberNo) {
                response.put("success", false);
                response.put("message", "잘못된 요청입니다.");
                return response;
            }
            
            // 삭제 권한 확인 (본인이 쓴 댓글이거나 프로필 주인)
            boolean canDelete = (loginMember.getMemberNo() == fromMemberNo || // 본인이 쓴 댓글
                               loginMember.getMemberNo() == memberNo);        // 프로필 주인
            
            if (!canDelete) {
                response.put("success", false);
                response.put("message", "일촌평 삭제 권한이 없습니다.");
                return response;
            }
            
            // 일촌평 삭제
            IlchonComment ilchonComment = new IlchonComment();
            ilchonComment.setFromMemberNo(fromMemberNo);
            ilchonComment.setToMemberNo(toMemberNo);
            
            int result = miniHomeService.deleteIlchonComment(ilchonComment);
            
            if (result > 0) {
                response.put("success", true);
                response.put("message", "일촌평이 삭제되었습니다.");
            } else {
                response.put("success", false);
                response.put("message", "일촌평 삭제에 실패했습니다.");
            }
            
            return response;
        
        
       
    }
    
    /**
     * 일촌평 작성 권한 확인 - ProfileInterceptor와 동일한 로직
     */
    private boolean checkWritePermission(Member loginMember, int memberNo) {
        // 본인 페이지인 경우
        if (loginMember.getMemberNo() == memberNo) {
            return true;
        }
        
        // 일촌 관계 확인 - ProfileInterceptor와 동일한 로직
        Ilchon myRequest = new Ilchon();
        myRequest.setFromMemberNo(loginMember.getMemberNo());
        myRequest.setToMemberNo(memberNo);
        
        int myAcceptedCount = miniHomeService.findAcceptedIlchon(myRequest);
        
        Ilchon theirRequest = new Ilchon();
        theirRequest.setFromMemberNo(memberNo);
        theirRequest.setToMemberNo(loginMember.getMemberNo());
        
        int theirAcceptedCount = miniHomeService.findAcceptedIlchon(theirRequest);
        
        // 일촌 관계면 작성 가능
        boolean isIlchon = (myAcceptedCount > 0 || theirAcceptedCount > 0);
        
        return isIlchon;
    }
    
  
}