package com.featherworld.project.board.model.service;

import com.featherworld.project.board.model.dto.BoardType;

import java.util.List;

public interface BoardTypeService {

    /** 현재 회원의 게시판 종류 번호(boardCode) 조회
     * @author Jiho
     * @param memberNo 현재 조회 중인 회원 번호
     */
    List<BoardType> selectBoardType(int memberNo);

    /** 현재 회원의 새로운 게시판 생성
     * @author Jiho
     * @param memberNo 게시판을 생성하는 회원 번호
     * @return result : 1(성공), 0(실패)
     */
    int insertBoardType(int memberNo);
}
