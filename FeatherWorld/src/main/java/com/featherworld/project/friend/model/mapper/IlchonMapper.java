package com.featherworld.project.friend.model.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.session.RowBounds;

import com.featherworld.project.friend.model.dto.Ilchon;
@Mapper
public interface IlchonMapper {
	
	/**일촌 리스트를 select하는 함수(IS_ILCHON = 'Y' 한정)
	 * @param loginMemberNo
	 * @param rowBounds
	 * @return
	 */
	List<Ilchon> select(int loginMemberNo, RowBounds rowBounds);
	
	/**일촌 리스트 명수를 count하는 함수(IS_ILCHON = 'Y' 한정)
	 * @param loginMemberNo
	 * @return
	 */
	int countIlchons(int loginMemberNo);

}
