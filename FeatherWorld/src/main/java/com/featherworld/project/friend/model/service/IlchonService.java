package com.featherworld.project.friend.model.service;

import java.util.Map;


import com.featherworld.project.friend.model.dto.Ilchon;
import com.featherworld.project.member.model.dto.Member;

public interface IlchonService {

	Map<String, Object> selectIlchonMemberList(int loginMemberNo, int cp);

	/*Ilchon updateIlchon();*/
	
	int updateIlchonNickname(int loginMemberNo, int memberNo,String nickname);
	public Ilchon selectOne(int loginMemberNo, int memberNo);

	int insertNewIlchon(int loginMemberNo, int memberNo);
}
