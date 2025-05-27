package com.featherworld.project.miniHome.model.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.featherworld.project.board.model.dto.Board;
import com.featherworld.project.friend.model.dto.Ilchon;
import com.featherworld.project.member.model.dto.Member;
import com.featherworld.project.member.model.dto.Today;
import com.featherworld.project.miniHome.model.mapper.MiniHomeMapper;

@Service
@Transactional(rollbackFor = Exception.class)
@PropertySource("classpath:/config.properties")
public class MiniHomeServiceImpl implements MiniHomeService {

    @Autowired
    private MiniHomeMapper mapper;

    // ================ 일촌평 관련 (ILCHON 테이블 통합) ================
    
    
    @Override
    public List<Ilchon> getIlchonComments(int memberNo) {
        return mapper.getIlchonComments(memberNo);
    }
    
    @Override
    public int updateIlchonFromComment(Ilchon ilchonRelation) {
        return mapper.updateIlchonFromComment(ilchonRelation);
    }
    
    @Override
    public int updateIlchonToComment(Ilchon reverseRelation) {
        return mapper.updateIlchonToComment(reverseRelation);
    }
    
    @Override
    public int deleteIlchonFromComment(Ilchon ilchonRelation) {
        return mapper.deleteIlchonFromComment(ilchonRelation);
    }
    
    @Override
    public int deleteIlchonToComment(Ilchon ilchonRelation) {
        return mapper.deleteIlchonToComment(ilchonRelation);
    }

    // ================ 회원 및 일촌 관계 ================
    
    @Override
    public Member findmember(int memberNo) {
        return mapper.findmember(memberNo);
    }
    
    @Override
    public int findIlchon(Ilchon friend) {
        return mapper.findIlchon(friend);
    }
    
    @Override
    public int findPendingIlchon(Ilchon theirRequest) {
        return mapper.findPendingIlchon(theirRequest);
    }
    
    @Override
    public int sendFollowRequest(Ilchon followRequest) {
        return mapper.sendFollowRequest(followRequest);
    }
    
    @Override
    public int getFollowerCount(int memberNo) {
        return mapper.getFollowerCount(memberNo);
    }
    
    @Override
    public int getFollowingCount(int memberNo) {
        return mapper.getFollowingCount(memberNo);
    }
    
    @Override
    public int getPendingFollowerCount(int memberNo) {
        return mapper.getPendingFollowerCount(memberNo);
    }

    // ================ 방문자 관련 ================
    
    @Override
    public int todayAdd(Today today) {
        return mapper.todayAdd(today);
    }
    
    @Override
    public int todayCount(Today today) {
        return mapper.todayCount(today);
    }
    
    @Override
    public int todayConfirm(Today today) {
        return mapper.todayConfirm(today);
    }
    
    @Override
    public int totalCount(int memberNo) {
        return mapper.totalCount(memberNo);
    }

    // ================ 게시글 및 방명록 ================
    
    @Override
    public List<Board> getRecentBoards(int memberNo) {
        return mapper.getRecentBoards(memberNo);
    }
    
    @Override
    public int getTotalBoardCount(int memberNo) {
        return mapper.getTotalBoardCount(memberNo);
    }
    
    @Override
    public int getTotalGuestBookCount(int memberNo) {
        return mapper.getTotalGuestBookCount(memberNo);
    }



	

	
}