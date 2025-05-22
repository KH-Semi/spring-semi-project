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
    public int insertBoardType(BoardType boardType) {
        return mapper.insertBoardType(boardType);
    }

    @Override
    public int deleteBoardType(BoardType boardType) {

        // 현재 회원의 게시판 목록을 가져옴
        List<BoardType> boardTypes = mapper.selectBoardType(boardType.getMemberNo());

        // 현재 회원이 해당 게시판을 가지고 있는지 확인
        for(BoardType memberBoard : boardTypes) {

            // 가지고 있다면 mapper 호출 & return
            if(memberBoard.getBoardCode() == boardType.getBoardCode()) {
                return mapper.deleteBoardType(boardType);
            }
        }

        return 0;
    }
}
