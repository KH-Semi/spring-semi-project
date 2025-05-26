package com.featherworld.project.miniHome.model.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.featherworld.project.friend.model.dto.Ilchon;
import com.featherworld.project.friend.model.dto.IlchonComment;
import com.featherworld.project.member.model.dto.Member;
import com.featherworld.project.miniHome.model.dto.MiniHomeRecentBoard; // ★ 필수 import
import com.featherworld.project.member.model.dto.Today;
import com.featherworld.project.miniHome.model.mapper.MiniHomeMapper;

@Service
@Transactional(rollbackFor = Exception.class)
@PropertySource("classpath:/config.properties")

public class MiniHomeServiceImpl implements MiniHomeService {

    @Autowired
    private MiniHomeMapper mapper;
    
 

    // 파일 업로드 테스트
    @Override
    public String fileUpload(MultipartFile uploadFile) throws Exception {
        return null;
    }

    @Override
    public String fileUpload1(MultipartFile uploadFile) throws Exception {
        return null;
    }

    @Override
    public int fileUpload2(MultipartFile uploaFile, int memberNo) throws Exception {
        return 0;
    }

    // 미니홈 주인 정보 조회
    @Override
    public Member findmember(int memberNo) {
        return mapper.findmember(memberNo);
    }

    // 일촌 여부 확인
    @Override
    public int findilchon(Ilchon friend) {
        return mapper.findilchon(friend);
    }

    // 최근 게시글 조회
    @Override
    public List<MiniHomeRecentBoard> getRecentBoards(int memberNo) {
        return mapper.selectRecentBoards(memberNo);
    }

    // 일촌평 리스트 조회
    @Override
    public List<IlchonComment> getIlchonComments(int memberNo) {
        return mapper.selectIlchonComments(memberNo);
    }

	//홈피주인 의대한 정보 가져오기
	@Override
	public Member findmember(int memberNo) {
		
		return mapper.findmember(memberNo);
	}
		
	// 일촌인지 아닌지확인
	@Override
	public int findilchon(Ilchon friend) {
		return mapper.findilchon(friend);
	
	}

	// 방문자가 존재한다면 그사람의 홈피의 today insert 함
	@Override
	public int todayAdd(Today today) {
		
		return mapper.todayAdd(today);
	}

	// 홈피주인의 today 의 수를 가져옴
	@Override
	public int todayCount(Today today) {
		
		return mapper.todayCount(today);
	}

	//로그인 회원이 오늘 방문했는지 찾는 메서드
	@Override
	public int todayConfirm(Today today) {
		
		return mapper.todayConfirm(today);
	}

	@Override
	public int totalCount(int memberNo) {
		
		return mapper.totalCount(memberNo);
	}
	
	// 남이 나를 일촌신청한수 (내가 일촌수락까지 한상태로)
	@Override
	public int getFollowerCount(int memberNo) {
		
		return mapper.getFollowerCount(memberNo);
	}
	
	// 내가 일촌신청한수 (상대방이 수락까찌 한상태)
	@Override
	public int getFollowingCount(int memberNo) {
		
		return mapper.getFollowingCount(memberNo);
	}
	
	//미수락된 팔로워 수가으면 나오게끔
	@Override
	public int getPendingFollowerCount(int memberNo) {
		// TODO Auto-generated method stub
		return mapper.getPendingFollowerCount(memberNo);
	}
	
	//일촌신청하기
	@Override
	public int sendFollowRequest(Ilchon followRequest) {
	
		return mapper.sendFollowRequest(followRequest);
	}

	@Override
	public int findPendingIlchon(Ilchon theirRequest) {
		
		return mapper.findPendingIlchon(theirRequest);
	}

	@Override
	public int findAcceptedIlchon(Ilchon myRequest) {
		
		return mapper.findAcceptedIlchon(myRequest);
	}
	
	
}

