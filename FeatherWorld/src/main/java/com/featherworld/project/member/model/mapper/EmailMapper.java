package com.featherworld.project.member.model.mapper;

import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface EmailMapper {

	/** 기존에 이메일이 있다면 인증키 수정
	 * @param map
	 * @return
	 * @author 영민
	 */
	int updateAuthKey(Map<String, String> map);

	/** 기존에 이메일이 아예 없었으면 인증키 최초발급
	 * @param map
	 * @return
	 * @author 영민
	 */
	int insertAuthKey(Map<String, String> map);

}
