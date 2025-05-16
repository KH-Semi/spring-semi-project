package com.featherworld.project.member.model.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.featherworld.project.member.model.dto.Member;
import com.featherworld.project.member.model.mapper.MemberMapper;

import lombok.extern.slf4j.Slf4j;

/** 서비스 인터페이스를 상속받는 서비스클래스
 * @author 영민
 */
@Transactional(rollbackFor = Exception.class) 
@Service 
@Slf4j 
public class MemberServiceImpl implements MemberService {
	
	@Autowired
	private MemberMapper mapper; 
	
	@Autowired
	private BCryptPasswordEncoder bcrypt; 
	//암호화 객체 의존성 주입

	// --------------------------------------------
	
	/** 회원가입 메서드
	 *	@author 영민
	 *
	 */
	@Override
	public int signUp(Member inputMember, String[] memberAddress) {
	    
	    // 주소 처리 로직 
	    if(!inputMember.getMemberAddress().equals(",,")) { 
	        String address = String.join("^^^", memberAddress);
	        inputMember.setMemberAddress(address);
	    } else {
	        inputMember.setMemberAddress(null);
	    }
	    
	    // 비밀번호 암호화
	    String encPw = bcrypt.encode(inputMember.getMemberPw());
	    inputMember.setMemberPw(encPw);
	    
	    // 회원 가입 처리
	    int result = mapper.signUp(inputMember);
	    
	    // 확인용 ..
	    log.info("생성된 회원 번호: {}", inputMember.getMemberNo());
	    
	    // 회원 가입이 성공한 경우에만 기본 게시판 타입 생성
	    if(result > 0) {
	     
	    	int memberNo	= inputMember.getMemberNo();
	    	
	      result = mapper.setDefaultBoardType(memberNo);
	        
	      log.info("기본 게시판 타입 설정 결과: {}", result);
	    }
	    
	    return result;
	}
	
	/** 회원가입이 되었을때 default로 boardType을 하나 생성해주는 메서드
	 *  (회원가입이 성공하면 memberNo가 생겨서 그memberNo로 만들어줌)
	 *	@author 영민
	 */
	@Override
	
	public int setDefaultBoardType(int memberNo) {
		
		return mapper.setDefaultBoardType(memberNo);
	}

	/** 로그인 메서드
	 * @author 영민
	 */
	@Override
	public Member login(Member inputMember) {
		
		Member loginMember = mapper.login(inputMember.getMemberEmail());
		
		if (loginMember == null) return null; // 조회해도 맞는 이메일이없을때
		
		
		if(!bcrypt.matches(inputMember.getMemberPw(),loginMember.getMemberPw()))return null;
		// 로그인회원의 비밀번호와 입력받은 비밀번호가 같지않다면
		
		loginMember.setMemberPw(null); // 로그인 비밀번호 혹시몰라 제거
		
		
		return loginMember;
	}

	/** 회원입도중 이메일 중복확인
	 *@author 영민
	 */
	@Override
	public int checkEmail(String memberEmail) {
		
		return mapper.checkEmail(memberEmail);
	}
}
