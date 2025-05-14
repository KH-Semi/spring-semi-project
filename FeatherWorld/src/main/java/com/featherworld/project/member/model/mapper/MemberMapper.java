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

}
