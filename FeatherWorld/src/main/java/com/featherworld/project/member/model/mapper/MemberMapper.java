package com.featherworld.project.member.model.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.boot.json.JsonWriter.Member;

@Mapper
public interface MemberMapper {
	
	Member selectMemberByNo(@Param("memberNo") int memberNo);
}
