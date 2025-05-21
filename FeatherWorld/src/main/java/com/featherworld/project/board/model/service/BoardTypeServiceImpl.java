package com.featherworld.project.board.model.service;

import com.featherworld.project.board.model.dto.BoardType;
import com.featherworld.project.board.model.mapper.BoardMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BoardTypeServiceImpl implements BoardTypeService {

    @Autowired
    BoardMapper mapper;

    // 현재 회원의 게시판 종류 번호(boardCode) 조회
    @Override
    public List<BoardType> selectBoardType(int memberNo) {
        return mapper.selectBoardType(memberNo);
    }

    @Override
    public int insertBoardType(int memberNo) {
        return 0;
    }


}
