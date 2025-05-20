package com.featherworld.project.member.model.service;

import java.util.List;
import java.util.Map;

import com.featherworld.project.member.model.dto.Member;

/** 멤버service 인터페이스
 * @author 영민
 */
public interface MemberService {

	/** 회원가입 메서드
	 * @author 영민
	 * @param inputMember
	 * @param memberAddress
	 * @return
	 */
	int signUp(Member inputMember, String[] memberAddress);

	/** 첫회원가입시 boardType을 하나 만들어주는 메서드
	 * @author 영민
	 * @param memberNo
	 * @return
	 */
	int setDefaultBoardType(int memberNo);

	/** 로그인 서비스
	 * @param inputMember
	 * @return
	 */
	Member login(Member inputMember);

	/** 이메일 중복 확인 서비스
	 * @param memberEmail
	 * @return
	 */
	int checkEmail(String memberEmail);

	
	/** 가입된 회원의 이메일 찾기
	 * @param inputMember
	 * @return
	 */
	Member findId(Member inputMember);

	/** 가입된 회원의 비밀번호 변경
	 * @param map
	 * @return
	 * @author 영민
	 */
	int resetPassword(Map<String, String> map);

	/** 메인홈의 회원들을 검색하는 기능
	 * @param memberName
	 * @return
	 * @author 영민
	 */
	List<Member> searchMember(String memberName);

	/** 회원가입중 전화번호 입력값이 중복인지 확인
	 * @param memberTel
	 * @return
	 * @author 영민
	 */
	int checkTel(int memberTel);

}
