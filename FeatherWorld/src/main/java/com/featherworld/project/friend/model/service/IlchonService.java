package com.featherworld.project.friend.model.service;

import java.util.Map;


import com.featherworld.project.friend.model.dto.Ilchon;

public interface IlchonService {

	Map<String, Object> selectIlchonMemberList(int loginMemberNo, int cp);

	/*Ilchon updateIlchon();*/
	
	int updateIlchonNickname(int loginMemberNo, int memberNo,String nickname);
	public Ilchon selectOne(int loginMemberNo, int memberNo);
}
