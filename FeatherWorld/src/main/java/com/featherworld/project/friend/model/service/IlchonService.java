package com.featherworld.project.friend.model.service;

import java.util.List;
import java.util.Map;

import com.featherworld.project.friend.model.dto.Ilchon;

public interface IlchonService {

	Map<String, Object> selectIlchonMemberList(int loginMemberNo, int cp);

}
