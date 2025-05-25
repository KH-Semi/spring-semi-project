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
}
