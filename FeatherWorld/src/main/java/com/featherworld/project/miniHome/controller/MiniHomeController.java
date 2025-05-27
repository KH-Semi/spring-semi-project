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
     */
    @GetMapping("{memberNo:[0-9]+}/minihome")
    public String miniHomePage(@PathVariable("memberNo") int memberNo,
                              @SessionAttribute(value = "loginMember", required = false) Member loginMember,
                              Model model) {
        
        System.out.println("=== 미니홈 페이지 로드 ===");
        System.out.println("memberNo: " + memberNo);
        
        List<Board> recentBoardList = miniHomeService.getRecentBoards(memberNo);
        System.out.println("최근 게시글 조회 완료: " + recentBoardList.size() + "개");
        
        // 일촌평 조회
        List<Ilchon> ilchonComments = miniHomeService.getIlchonComments(memberNo);
        System.out.println("일촌평 조회 결과: " + (ilchonComments != null ? ilchonComments.size() : "NULL") + "개");
        
        int totalBoardCount = miniHomeService.getTotalBoardCount(memberNo);
        int totalGuestBookCount = miniHomeService.getTotalGuestBookCount(memberNo);
        
        model.addAttribute("totalBoardCount", totalBoardCount);
        model.addAttribute("totalGuestBookCount", totalGuestBookCount);
        model.addAttribute("recentBoards", recentBoardList);
        model.addAttribute("ilchonComments", ilchonComments); 
        model.addAttribute("loginMember", loginMember);
        model.addAttribute("memberNo", memberNo);
        
        System.out.println("=== 미니홈 페이지 로드 완료 ===");
        
        return "miniHome/miniHome";
    }

    /**
     * 일촌평 작성/수정
     */
    @PostMapping("{memberNo:[0-9]+}/ilchoncomment")
    @ResponseBody
    public Map<String, Object> createOrUpdateIlchonComment(
            @PathVariable("memberNo") int pageOwnerNo,
            @RequestBody Map<String, Object> requestData,
            @SessionAttribute("loginMember") Member loginMember) {

        Map<String, Object> response = new HashMap<>();

        try {
            System.out.println("=== 일촌평 작성 시작 ===");
            System.out.println("페이지 주인: " + pageOwnerNo);
            System.out.println("작성자: " + loginMember.getMemberNo() + "(" + loginMember.getMemberName() + ")");
            
            if (loginMember == null) {
                response.put("success", false);
                response.put("message", "로그인이 필요합니다.");
                return response;
            }

            String commentContent = (String) requestData.get("ilchonCommentContent");
            
            // 내용 검증
            if (commentContent == null || commentContent.trim().isEmpty()) {
                response.put("success", false);
                response.put("message", "일촌평 내용을 입력해주세요.");
                return response;
            }

            if (commentContent.length() > 30) {
                response.put("success", false);
                response.put("message", "일촌평은 30자 이내로 작성해주세요.");
                return response;
            }

            // 권한 확인
            if (!checkWritePermission(loginMember, pageOwnerNo)) {
                response.put("success", false);
                response.put("message", "일촌평 작성 권한이 없습니다.");
                return response;
            }

            // 일촌평 저장 (양방향 시도)
            int result = saveIlchonComment(loginMember.getMemberNo(), pageOwnerNo, commentContent.trim());

            if (result > 0) {
                System.out.println("✅ 일촌평 작성 성공");
                response.put("success", true);
                response.put("message", "일촌평이 작성되었습니다.");
            } else {
                System.out.println("❌ 일촌평 작성 실패");
                response.put("success", false);
                response.put("message", "일촌평 작성에 실패했습니다.");
            }

        } catch (Exception e) {
            System.err.println("❌ 일촌평 작성 중 예외: " + e.getMessage());
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
            @PathVariable("memberNo") int pageOwnerNo,
            @RequestBody Map<String, Object> requestData,
            @SessionAttribute("loginMember") Member loginMember) {
        
        Map<String, Object> response = new HashMap<>();
        
        try {
            System.out.println("=== 일촌평 삭제 시작 ===");
            
            if (loginMember == null) {
                response.put("success", false);
                response.put("message", "로그인이 필요합니다.");
                return response;
            }
            
            int authorNo = (Integer)requestData.get("authorNo");  // 실제 작성자 번호
            
            System.out.println("페이지 주인: " + pageOwnerNo);
            System.out.println("실제 작성자: " + authorNo);
            System.out.println("삭제 요청자: " + loginMember.getMemberNo());
            
            // 삭제 권한 확인 (작성자이거나 페이지 주인)
            boolean canDelete = (loginMember.getMemberNo() == authorNo || 
                               loginMember.getMemberNo() == pageOwnerNo);
            
            if (!canDelete) {
                response.put("success", false);
                response.put("message", "일촌평 삭제 권한이 없습니다.");
                return response;
            }
            
            // 일촌평 삭제 (양방향 시도)
            int result = deleteIlchonCommentInternal(authorNo, pageOwnerNo);
            
            if (result > 0) {
                response.put("success", true);
                response.put("message", "일촌평이 삭제되었습니다.");
            } else {
                response.put("success", false);
                response.put("message", "일촌평 삭제에 실패했습니다.");
            }
            
        } catch (Exception e) {
            response.put("success", false);
            response.put("message", "일촌평 삭제 중 오류가 발생했습니다.");
            e.printStackTrace();
        }
        
        return response;
    }
    /**
     * 일촌평 저장 (양방향 시도)
     */
    private int saveIlchonComment(int authorNo, int pageOwnerNo, String content) {
        System.out.println("=== 일촌평 저장 시도 ===");
        
        // 1차: 작성자→페이지주인 방향으로 FROM_COMMENT에 저장
        System.out.println("1차 시도: FROM=" + authorNo + ", TO=" + pageOwnerNo + ", FROM_COMMENT");
        Ilchon relation1 = new Ilchon();
        relation1.setFromMemberNo(authorNo);
        relation1.setToMemberNo(pageOwnerNo);
        relation1.setFromComment(content);
        
        int result = miniHomeService.updateIlchonFromComment(relation1);
        System.out.println("1차 결과: " + result);
        
        if (result == 0) {
            // 2차: 페이지주인→작성자 방향으로 TO_COMMENT에 저장
            System.out.println("2차 시도: FROM=" + pageOwnerNo + ", TO=" + authorNo + ", TO_COMMENT");
            Ilchon relation2 = new Ilchon();
            relation2.setFromMemberNo(pageOwnerNo);
            relation2.setToMemberNo(authorNo);
            relation2.setToComment(content);
            
            result = miniHomeService.updateIlchonToComment(relation2);
            System.out.println("2차 결과: " + result);
        }
        
        return result;
    }

    /**
     * 일촌평 삭제 (양방향 시도) - 내부 메소드
     */
    private int deleteIlchonCommentInternal(int authorNo, int pageOwnerNo) {
        System.out.println("=== 일촌평 삭제 시도 ===");
        
        // 1차: 작성자→페이지주인 방향의 FROM_COMMENT 삭제
        System.out.println("1차 삭제 시도: FROM=" + authorNo + ", TO=" + pageOwnerNo + ", FROM_COMMENT");
        Ilchon relation1 = new Ilchon();
        relation1.setFromMemberNo(authorNo);
        relation1.setToMemberNo(pageOwnerNo);
        
        int result = miniHomeService.deleteIlchonFromComment(relation1);
        System.out.println("1차 삭제 결과: " + result);
        
        if (result == 0) {
            // 2차: 페이지주인→작성자 방향의 TO_COMMENT 삭제
            System.out.println("2차 삭제 시도: FROM=" + pageOwnerNo + ", TO=" + authorNo + ", TO_COMMENT");
            Ilchon relation2 = new Ilchon();
            relation2.setFromMemberNo(pageOwnerNo);
            relation2.setToMemberNo(authorNo);
            
            result = miniHomeService.deleteIlchonToComment(relation2);
            System.out.println("2차 삭제 결과: " + result);
        }
        
        return result;
    }
   
    /**
     * 일촌평 작성 권한 확인
     */
    private boolean checkWritePermission(Member loginMember, int pageOwnerNo) {
        // 본인 페이지는 항상 가능
        if (loginMember.getMemberNo() == pageOwnerNo) {
            return true;
        }
        
        // 일촌 관계 확인
        Ilchon relationCheck = new Ilchon();
        relationCheck.setFromMemberNo(loginMember.getMemberNo());
        relationCheck.setToMemberNo(pageOwnerNo);
        
        int acceptedCount = miniHomeService.findIlchon(relationCheck);
        return acceptedCount > 0;
    }
}