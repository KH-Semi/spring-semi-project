package com.featherworld.project.miniHome.model.mapper;

import org.apache.ibatis.annotations.Mapper;

import com.featherworld.project.friend.model.dto.Ilchon;
import com.featherworld.project.member.model.dto.Member;

@Mapper
public interface MiniHomeMapper {

	Member findmember(int memberNo);

	int findilchon(Ilchon friend);

}
