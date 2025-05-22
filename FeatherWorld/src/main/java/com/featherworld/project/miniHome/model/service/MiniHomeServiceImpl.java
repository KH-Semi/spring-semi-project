package com.featherworld.project.miniHome.model.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.featherworld.project.friend.model.dto.Ilchon;
import com.featherworld.project.member.model.dto.Member;
import com.featherworld.project.miniHome.model.mapper.MiniHomeMapper;

@Service
@Transactional(rollbackFor = Exception.class)
@PropertySource("classpath:/config.properties")
public class MiniHomeServiceImpl implements MiniHomeService{

	@Autowired
	private MiniHomeMapper mapper;
	
// 파일 업로드 테스트 1
	public String fileUpload(MultipartFile uploadFile) throws Exception {
		
		return null;
	}

	@Override
	public String fileUpload1(MultipartFile uploadFile) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int fileUpload2(MultipartFile uploaFile, int memberNo) throws Exception {
		// TODO Auto-generated method stub
		return 0;
	}

	//홈피주인 의대한 정보 가져오기
	@Override
	public Member findmember(int memberNo) {
		
		
		return mapper.findmember(memberNo);
	}

	@Override
	public int findilchon(Ilchon friend) {
	
		return mapper.findilchon(friend);
	}
}