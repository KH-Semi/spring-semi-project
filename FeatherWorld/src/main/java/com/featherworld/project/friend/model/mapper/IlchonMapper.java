package com.featherworld.project.friend.model.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

import com.featherworld.project.friend.model.dto.Ilchon;
@Mapper
public interface IlchonMapper {
	
	
	//Ilchon selectOne(int memberNo1, int memberNo2);
	Ilchon selectOne(Map<String, Object> map);
	/**일촌 리스트를 select하는 함수(IS_ILCHON = 'Y' 한정)
	 * @param loginMemberNo
	 * @param rowBounds
	 * @return
	 */
	List<Ilchon> selectPagination(int loginMemberNo, RowBounds rowBounds);
	
	/**일촌 리스트 명수를 count하는 함수(IS_ILCHON = 'Y' 한정)
	 * @param loginMemberNo
	 * @return
	 */
	int countIlchons(int loginMemberNo);
	int updateToIlchonNickName(Map<String, Object> paramMap);
	int updateFromIlchonNickName(Map<String, Object> paramMap);
	//int updateToIlchonNickName(@Param("loginMemberNo") int loginMemberNo/*session*/,@Param("memberNo") int memberNo,@Param("nickname") String nickname);
	//int updateFromIlchonNickName(@Param("loginMemberNo")int loginMemberNo/*session*/,@Param("memberNo") int memberNo,@Param("nickname") String nickname);
	
}
