package com.featherworld.project.common.interceptor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.featherworld.project.friend.model.dto.Ilchon;
import com.featherworld.project.member.model.dto.Member;
import com.featherworld.project.member.model.dto.Today;
import com.featherworld.project.miniHome.model.service.MiniHomeService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@Component
public class ProfileInterceptor implements HandlerInterceptor {

    @Autowired
    private MiniHomeService miniHomeService;

    /**
     * leftfragment를 사용하지 않는 페이지들 확인
     */
    private boolean shouldExcludeFromProfile(String requestURI) {
        if (requestURI.matches(".*/(\\d+)/board.*")) {
            return true;
        }
        return false;
    }

    @Override
    public void postHandle(HttpServletRequest request, 
                          HttpServletResponse response, 
                          Object handler, 
                          ModelAndView modelAndView) throws Exception {
        
        if (modelAndView == null || modelAndView.getViewName() == null) {
            return;
        }
        
        String requestURI = request.getRequestURI();
        System.out.println("🔍 ProfileInterceptor 요청 URI: " + requestURI);
        
        if (!requestURI.matches(".*/(\\d+)/.*")) {
            System.out.println("❌ memberNo 패턴에 맞지 않음: " + requestURI);
            return;
        }
        
        if (shouldExcludeFromProfile(requestURI)) {
            System.out.println("❌ 제외된 페이지: " + requestURI);
            return;
        }
        
        try {
            int memberNo = extractMemberNoFromURI(requestURI);
            if (memberNo <= 0) {
                System.out.println("❌ memberNo 추출 실패: " + requestURI);
                return;
            }
            
            HttpSession session = request.getSession();
            Member loginMember = (Member) session.getAttribute("loginMember");
            
            // 🔍 세션 확인 로그 추가
            System.out.println("🔍 세션 loginMember: " + (loginMember != null ? loginMember.getMemberName() + "(" + loginMember.getMemberNo() + ")" : "NULL"));
            
            setCommonProfileData(memberNo, loginMember, modelAndView);
            
            if (loginMember != null) {
                processVisitor(memberNo, loginMember);
            }
            
            System.out.println("✅ ProfileInterceptor 적용 완료: " + requestURI + " (memberNo: " + memberNo + ")");
            
        } catch (Exception e) {
            System.err.println("ProfileInterceptor 오류: " + e.getMessage());
            e.printStackTrace();
        }
    }	

    private void setCommonProfileData(int memberNo, Member loginMember, ModelAndView modelAndView) {
        
        Member member = miniHomeService.findmember(memberNo);
        System.out.println("🔍 조회된 멤버: " + (member != null ? member.getMemberName() : "null"));
        
        Today todayQuery = new Today();
        todayQuery.setHomeNo(memberNo);
        int todayCount = miniHomeService.todayCount(todayQuery);
        int totalCount = miniHomeService.totalCount(memberNo);
        int followerCount = miniHomeService.getFollowerCount(memberNo);
        int followingCount = miniHomeService.getFollowingCount(memberNo);
        
        boolean hasPendingFollowers = false;
        if (loginMember != null && loginMember.getMemberNo() == memberNo) {
            int pendingFollowerCount = miniHomeService.getPendingFollowerCount(memberNo);
            hasPendingFollowers = (pendingFollowerCount > 0);
        }
        
        boolean isIlchon = false;
        boolean isPendingRequest = false;
        
        // ✅ 수정된 부분: 본인이 아닐 때만 일촌 상태 확인
        if (loginMember != null && loginMember.getMemberNo() != memberNo) {
            // 🔍 디버깅 시작
            System.out.println("=== 일촌 상태 디버깅 시작 ===");
            System.out.println("로그인 사용자: " + loginMember.getMemberName() + " (번호: " + loginMember.getMemberNo() + ")");
            System.out.println("프로필 주인 번호: " + memberNo);
            
            Ilchon myRequest = new Ilchon();
            myRequest.setFromMemberNo(loginMember.getMemberNo());
            myRequest.setToMemberNo(memberNo);
            
            int myAcceptedCount = miniHomeService.findAcceptedIlchon(myRequest);
            int myPendingCount = miniHomeService.findPendingIlchon(myRequest);
            
            System.out.println("내가 신청한 수락된 일촌: " + myAcceptedCount);
            System.out.println("내가 신청한 대기중 신청: " + myPendingCount);
            
            Ilchon theirRequest = new Ilchon();
            theirRequest.setFromMemberNo(memberNo);
            theirRequest.setToMemberNo(loginMember.getMemberNo());
            
            int theirAcceptedCount = miniHomeService.findAcceptedIlchon(theirRequest);
            System.out.println("상대방이 신청한 수락된 일촌: " + theirAcceptedCount);
            
            isIlchon = (myAcceptedCount > 0 || theirAcceptedCount > 0);
            isPendingRequest = (myPendingCount > 0);
            
            System.out.println("최종 isIlchon: " + isIlchon);
            System.out.println("최종 isPendingRequest: " + isPendingRequest);
            System.out.println("=== 일촌 상태 디버깅 끝 ===");
        } else if (loginMember != null && loginMember.getMemberNo() == memberNo) {
            // ✅ 추가된 부분: 본인 페이지일 때 로그
            System.out.println("=== 본인 페이지 확인 ===");
            System.out.println("로그인 사용자: " + loginMember.getMemberName() + " (번호: " + loginMember.getMemberNo() + ")");
            System.out.println("프로필 주인 번호: " + memberNo);
            System.out.println("본인 페이지이므로 일촌평 작성 가능");
            System.out.println("=== 본인 페이지 확인 끝 ===");
        }
        
        modelAndView.addObject("totalCount", totalCount);
        modelAndView.addObject("todayCount", todayCount);
        modelAndView.addObject("member", member);
        modelAndView.addObject("followerCount", followerCount);
        modelAndView.addObject("followingCount", followingCount);
        modelAndView.addObject("hasPendingFollowers", hasPendingFollowers);
        modelAndView.addObject("memberNo", memberNo);
        modelAndView.addObject("isIlchon", isIlchon);
        modelAndView.addObject("isPendingRequest", isPendingRequest);
        
        // 🔍 ModelAndView 설정 확인 로그 - 추가 정보
        System.out.println("✅ ModelAndView 설정 완료:");
        System.out.println("   - memberNo: " + memberNo);
        System.out.println("   - isIlchon: " + isIlchon);
        System.out.println("   - loginMember 존재: " + (loginMember != null));
        if (loginMember != null) {
            System.out.println("   - loginMember.memberNo: " + loginMember.getMemberNo());
            System.out.println("   - 본인 페이지?: " + (loginMember.getMemberNo() == memberNo));
            System.out.println("   - Write 버튼 표시 조건: " + (isIlchon || loginMember.getMemberNo() == memberNo));
        }
        
        System.out.println("✅ 프로필 데이터 설정 완료 - 방문자: " + totalCount + ", 오늘: " + todayCount);
    }

    private void processVisitor(int memberNo, Member loginMember) {
        if (loginMember != null && loginMember.getMemberNo() != memberNo) {
            Today today = new Today(); 
            today.setHomeNo(memberNo);
            today.setVisitNo(loginMember.getMemberNo());
            
            int todayConfirm = miniHomeService.todayConfirm(today);
            
            if (todayConfirm == 0) {
                miniHomeService.todayAdd(today);
                System.out.println("✅ 새로운 방문자 기록 추가: " + loginMember.getMemberName());
            }
        }
    }

    private int extractMemberNoFromURI(String requestURI) {
        String[] pathSegments = requestURI.split("/");
        for (String segment : pathSegments) {
            if (segment.matches("\\d+")) {
                System.out.println("🔍 추출된 memberNo: " + segment);
                return Integer.parseInt(segment);
            }
        }
        return -1;
    }
}