package com.featherworld.project.friend.model.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.featherworld.project.common.dto.Pagination;
import com.featherworld.project.friend.model.dto.Ilchon;
import com.featherworld.project.friend.model.mapper.IlchonMapper;


@Service
public class IlchonServiceImpl implements IlchonService {

	
	@Autowired
	private IlchonMapper mapper;
	@Override
	public Map<String, Object> selectIlchonMemberList(int loginMemberNo, int cp) {
		// TODO Auto-generated method stub
		
		
		int ilchonsCount =  mapper.countIlchons(loginMemberNo);
		
		// pagination 객체 생성 
	    Pagination pagination = new Pagination(cp, ilchonsCount);
		
		// 지정된 inchon들의 목록 조회
		/*
		 * ROWBOUNDS 객체 (MyBatis 제공 객체)
		 * : 지정된 크기만큼 건너 뛰고(offset)
		 * 제한된 크기만큼(limit)의 행을 조회하는 객체
		 * 
		 * --> 페이징 처리가 굉장히 간단해짐
		 * 
		 * */
		
		int limit = pagination.getLimit(); // 한 페이지당 default(=10개)
		
		int offset = (cp - 1) * limit;
		
		RowBounds rowBounds = new RowBounds(offset, limit);
		

		// Mapper 메서드 호출 시 원래 전달할 수 있는 매개변수 1개
		// -> 2개를 전달할 수 있는 경우가 있음
		// rowBounds를 이용할때!
		// -> 첫번째 매개변수 -> SQL 에 전달할 파라미터
		// -> 두번째 매개변수 -> RowBounds 객체 전달
		List<Ilchon> ilchons = mapper.select(loginMemberNo, rowBounds);
	
		// 4. 목록 조회 결과 + Pagination 객체를 Map으로 묶음
		Map<String, Object> map = new HashMap<>();
				
		map.put("pagination", pagination);
		map.put("ilchons", ilchons);
				
				
		return map;
	
	}

}
