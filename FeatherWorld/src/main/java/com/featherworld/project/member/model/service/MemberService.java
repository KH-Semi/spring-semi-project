package com.featherworld.project.member.model.service;

import java.util.Map;

import com.featherworld.project.member.model.dto.Member;

/** 멤버service 인터페이스
 * @author 영민
 */
public interface MemberService {

	/** 회원 여부 확인
	 * @author Jiho
	 * @param memberNo 현재 조회 중인 회원 번호
	 */
	int checkMember(int memberNo);

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
	 */
	int resetPassword(Map<String, String> map);

}
