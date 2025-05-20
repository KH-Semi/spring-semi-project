package com.featherworld.project.board.model.service;

import com.featherworld.project.board.model.dto.BoardType;

import java.util.List;

public interface BoardTypeService {

    /** 현재 회원의 게시판 종류 번호(boardCode) 조회
     * @author Jiho
     * @param memberNo : 현재 조회 중인 회원 번호
     */
    List<BoardType> selectBoardType(int memberNo);

}
