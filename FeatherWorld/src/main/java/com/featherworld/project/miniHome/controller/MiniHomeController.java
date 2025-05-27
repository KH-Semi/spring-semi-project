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
        
        System.out.println("=== 미니홈 페이지 로드 ===");
        System.out.println("memberNo: " + memberNo);
        
        List<Board> recentBoardList = miniHomeService.getRecentBoards(memberNo);
        System.out.println("최근 게시글 조회 완료: " + recentBoardList.size() + "개");
        
        // 일촌평 조회 - ILCHON 테이블에서 직접 조회
        System.out.println("=== 일촌평 조회 시작 ===");
        System.out.println("조회 대상 memberNo: " + memberNo);
        List<Ilchon> ilchonComments = miniHomeService.getIlchonComments(memberNo);
        System.out.println("일촌평 조회 결과: " + (ilchonComments != null ? ilchonComments.size() : "NULL") + "개");
        
        if (ilchonComments != null && !ilchonComments.isEmpty()) {
            for (int i = 0; i < ilchonComments.size(); i++) {
                Ilchon comment = ilchonComments.get(i);
                System.out.println("일촌평 " + (i+1) + ": " + comment.getFromNickname() + " -> " + comment.getToComment());
            }
        } else {
            System.out.println("조회된 일촌평이 없음");
        }
        
        int totalBoardCount = miniHomeService.getTotalBoardCount(memberNo);
        int totalGuestBookCount = miniHomeService.getTotalGuestBookCount(memberNo);
        
        model.addAttribute("totalBoardCount", totalBoardCount);
        model.addAttribute("totalGuestBookCount", totalGuestBookCount);
        model.addAttribute("recentBoards", recentBoardList);
        model.addAttribute("ilchonComments", ilchonComments); 
        model.addAttribute("loginMember", loginMember);
        
        System.out.println("=== 미니홈 페이지 로드 완료 ===");
     // 디버깅: 모든 일촌 관계 확인
        System.out.println("=== 전체 일촌 관계 확인 ===");
        return "miniHome/miniHome";
    }
    
    /** 일촌평 작성/수정
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
            System.out.println("=== 일촌평 작성 시작 ===");
            System.out.println("URL memberNo: " + memberNo);
            System.out.println("로그인 사용자: " + (loginMember != null ? loginMember.getMemberNo() + "(" + loginMember.getMemberName() + ")" : "NULL"));
            
            if (loginMember == null) {
                response.put("success", false);
                response.put("message", "로그인해야됌");
                return response;
            }

            int toMemberNo = (Integer) requestData.get("toMemberNo");
            String commentContent = (String) requestData.get("ilchonCommentContent");
            
            System.out.println("toMemberNo: " + toMemberNo);
            System.out.println("commentContent: '" + commentContent + "'");

            // URL 검증
            if (memberNo != toMemberNo) {
                System.out.println("❌ URL 검증 실패: memberNo(" + memberNo + ") != toMemberNo(" + toMemberNo + ")");
                response.put("success", false);
                response.put("message", "잘못된 요청입니다.");
                return response;
            }

            // 일촌평 내용 검증
            if (commentContent == null || commentContent.trim().isEmpty()) {
                System.out.println("❌ 내용 검증 실패: 빈 내용");
                response.put("success", false);
                response.put("message", "일촌평 내용을 입력해주세요.");
                return response;
            }

            // 30자 제한
            if (commentContent.length() > 30) {
                System.out.println("❌ 길이 검증 실패: " + commentContent.length() + "자");
                response.put("success", false);
                response.put("message", "일촌평은 30자 이내로 작성해주세요.");
                return response;
            }

            // 권한 확인 - 일촌 관계인지 체크
            System.out.println("=== 권한 확인 ===");
            boolean canWrite = checkWritePermission(loginMember, memberNo);
            System.out.println("권한 확인 결과: " + canWrite);

            if (!canWrite) {
                System.out.println("❌ 권한 없음");
                response.put("success", false);
                response.put("message", "일촌평 작성 권한이 없습니다.");
                return response;
            }

            // 두 방향 모두 시도해서 성공한 쪽으로 업데이트
            int result = 0;
            
            // 1. FROM → TO 방향 시도 (loginMember가 작성자)
            System.out.println("=== 1차 시도: FROM → TO ===");
            Ilchon ilchonRelation = new Ilchon();
            ilchonRelation.setFromMemberNo(loginMember.getMemberNo());
            ilchonRelation.setToMemberNo(memberNo);
            ilchonRelation.setFromComment(commentContent.trim());
            
            System.out.println("FROM: " + loginMember.getMemberNo() + " → TO: " + memberNo);
            System.out.println("FROM_COMMENT: '" + commentContent.trim() + "'");
            
            result = miniHomeService.updateIlchonFromComment(ilchonRelation);
            System.out.println("1차 결과: " + result);
            
            // 2. 실패하면 TO → FROM 방향 시도  
            if (result == 0) {
                System.out.println("=== 2차 시도: TO → FROM ===");
                Ilchon reverseRelation = new Ilchon();
                reverseRelation.setFromMemberNo(memberNo);
                reverseRelation.setToMemberNo(loginMember.getMemberNo());
                reverseRelation.setToComment(commentContent.trim());
                
                System.out.println("FROM: " + memberNo + " → TO: " + loginMember.getMemberNo());
                System.out.println("TO_COMMENT: '" + commentContent.trim() + "'");
                
                result = miniHomeService.updateIlchonToComment(reverseRelation);
                System.out.println("2차 결과: " + result);
            }

            System.out.println("최종 결과: " + result);

            if (result > 0) {
                System.out.println("✅ 일촌평 작성 성공");
                response.put("success", true);
                response.put("message", "일촌평이 작성되었습니다.");
            } else {
                System.out.println("❌ 일촌평 작성 실패");
                response.put("success", false);
                response.put("message", "일촌평 작성에 실패했습니다.");
            }
            
            System.out.println("=== 일촌평 작성 끝 ===");

        } catch (Exception e) {
            System.err.println("❌ 일촌평 작성 중 예외 발생: " + e.getMessage());
            response.put("success", false);
            response.put("message", "일촌평 처리 중 오류가 발생했습니다.");
            e.printStackTrace();
        }

        return response;
    }
    	
    /** 
     * 일촌평 삭제
     */
    @DeleteMapping("{memberNo:[0-9]+}/ilchoncomment")
    @ResponseBody
    public Map<String, Object> deleteIlchonComment(
            @PathVariable("memberNo") int memberNo,
            @RequestBody Map<String, Object> requestData,
            @SessionAttribute("loginMember") Member loginMember) {
        
        Map<String, Object> response = new HashMap<>();
        
        try {
            System.out.println("=== 일촌평 삭제 시작 ===");
            
            // 로그인 체크
            if (loginMember == null) {
                response.put("success", false);
                response.put("message", "로그인이 필요합니다.");
                return response;
            }
            
            // 요청 데이터 추출
            int fromMemberNo = (Integer)requestData.get("fromMemberNo"); 
            int toMemberNo = (Integer)requestData.get("toMemberNo");
            
            System.out.println("URL memberNo: " + memberNo);
            System.out.println("요청 fromMemberNo: " + fromMemberNo);
            System.out.println("요청 toMemberNo: " + toMemberNo);
            System.out.println("로그인 사용자: " + loginMember.getMemberNo());
            
            // URL이 현재 페이지와 일치하는지 확인 (fromMemberNo 또는 toMemberNo 중 하나는 memberNo와 같아야 함)
            if (memberNo != fromMemberNo && memberNo != toMemberNo) {
                System.out.println("❌ URL 검증 실패: memberNo가 fromMemberNo나 toMemberNo와 일치하지 않음");
                response.put("success", false);
                response.put("message", "잘못된 요청입니다.");
                return response;
            }
            
            // 삭제 권한 확인 (작성자이거나 프로필 주인이어야 함)
            boolean canDelete = (loginMember.getMemberNo() == memberNo) ||      // 프로필 주인
                    (loginMember.getMemberNo() == fromMemberNo) ||   // FROM 멤버  
                    (loginMember.getMemberNo() == toMemberNo);       // TO 멤버
            
            System.out.println("삭제 권한 확인: " + canDelete);
            
            if (!canDelete) {
                response.put("success", false);
                response.put("message", "일촌평 삭제 권한이 없습니다.");
                return response;
            }
            
            int result = 0;
            
            // 직접 삭제 시도 - FROM_COMMENT인지 TO_COMMENT인지 확인
            System.out.println("=== 1차 시도: FROM_COMMENT 삭제 ===");
            Ilchon deleteRelation1 = new Ilchon();
            deleteRelation1.setFromMemberNo(fromMemberNo);
            deleteRelation1.setToMemberNo(toMemberNo);
            result = miniHomeService.deleteIlchonFromComment(deleteRelation1);
            System.out.println("1차 결과: " + result);
            
            if (result == 0) {
                System.out.println("=== 2차 시도: TO_COMMENT 삭제 ===");
                Ilchon deleteRelation2 = new Ilchon();
                deleteRelation2.setFromMemberNo(fromMemberNo);
                deleteRelation2.setToMemberNo(toMemberNo);
                result = miniHomeService.deleteIlchonToComment(deleteRelation2);
                System.out.println("2차 결과: " + result);
            }
            
            System.out.println("최종 삭제 결과: " + result);
            
            if (result > 0) {
                response.put("success", true);
                response.put("message", "일촌평이 삭제되었습니다.");
            } else {
                response.put("success", false);
                response.put("message", "일촌평 삭제에 실패했습니다.");
            }
            
            System.out.println("=== 일촌평 삭제 끝 ===");
            
        } catch (Exception e) {
            response.put("success", false);
            response.put("message", "일촌평 삭제 중 오류가 발생했습니다.");
            System.err.println("일촌평 삭제 오류: " + e.getMessage());
            e.printStackTrace();
        }
        
        return response;
    }
    
    /**
     * 일촌평 작성 권한 확인
     */
    private boolean checkWritePermission(Member loginMember, int memberNo) {
        System.out.println("=== 권한 확인 상세 ===");
        System.out.println("로그인 사용자: " + loginMember.getMemberNo());
        System.out.println("대상 memberNo: " + memberNo);
        
        // 본인 페이지인 경우
        if (loginMember.getMemberNo() == memberNo) {
            System.out.println("✅ 본인 페이지 - 권한 있음");
            return true;
        }
        
        // 일촌 관계 확인 (양방향 체크를 Mapper에서 처리)
        Ilchon relationCheck = new Ilchon();
        relationCheck.setFromMemberNo(loginMember.getMemberNo());
        relationCheck.setToMemberNo(memberNo);
        
        System.out.println("일촌 관계 확인: " + loginMember.getMemberNo() + " ↔ " + memberNo);
        int acceptedCount = miniHomeService.findIlchon(relationCheck);
        System.out.println("일촌 관계 수: " + acceptedCount);
        
        boolean result = (acceptedCount > 0);
        System.out.println("권한 결과: " + result);
        System.out.println("=== 권한 확인 끝 ===");
        
        return result;
        
    }
}