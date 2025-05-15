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
		
		//1.다음 api를 이용한 우편번호 + 주소와 상세주소를 구분자를, 이걸^^^로바꿈 
		
		log.info(inputMember.getMemberAddress()); // 주소 입력값 확인용 로그
		
		// 주소가 입력되어있는 경우
		if(!inputMember.getMemberAddress().equals(",,")) { 
			
			String address = String.join("^^^", memberAddress);
			// 구분자 추가해서 ,자리에 ^^^ 이걸로 바꿀꺼임
			
			inputMember.setMemberAddress(address);
		
				
		}else {// 주소가 입력되지 않은경우
			
			inputMember.setMemberAddress(null);
			//null 값 넣어버렷
		}
		
		//2. 비밀번호 암호화
		String encPw = bcrypt.encode(inputMember.getMemberPw());
		
		inputMember.setMemberPw(encPw);
		
		//3. 카카오 API  일딴 나중에 API 구현하면 대체할꺼임 일딴 ㄱㄷ ..
		
		int result = mapper.signUp(inputMember);
		
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
		
		
		//if(!bcrypt.matches(inputMember.getMemberPw(),loginMember.getMemberPw()))return null;
		// 로그인회원의 비밀번호와 입력받은 비밀번호가 같지않다면
		
		loginMember.setMemberPw(null); // 로그인 비밀번호 혹시몰라 제거
		
		
		return loginMember;
	}
}
