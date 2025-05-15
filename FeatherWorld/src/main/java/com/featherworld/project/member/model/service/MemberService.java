package com.featherworld.project.member.model.service;

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

}
