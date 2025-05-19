package com.featherworld.project.member.model.mapper;

import org.apache.ibatis.annotations.Mapper;
import com.featherworld.project.member.model.dto.Member;

/** 멤버 mapper 인터페이스
 * @author 영민
 * 
 */
@Mapper
public interface MemberMapper {

	/** 회원가입 메서드
	 * @author 영민
	 * @param inputMember
	 * @return
	 */
	int signUp(Member inputMember);

	/** 회원가입을 하고나서 boardtype을 바로 추가해주는 메서드
	 * @param memberNo
	 * @author 영민
	 * @return
	 */
	int setDefaultBoardType(int memberNo);

	/** 로그인을 할때 이메일이 있는지 조회하는 메서드
	 * @param memberEmail
	 * @return
	 * @author 영민
	 */
	Member login(String memberEmail);

	/** 회원가입도중 이메일 중복확인
	 * @param memberEmail
	 * @return
	 * @author 영민
	 */
	int checkEmail(String memberEmail);

	/** 가입된 회원의 이메일 찾기
	 * @param inputMember
	 * @return
	 * @author 영민
	 */
	Member findId(Member inputMember);
}
