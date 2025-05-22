package com.featherworld.project.board.controller;

import com.featherworld.project.board.model.dto.BoardType;
import com.featherworld.project.board.model.service.BoardService;
import com.featherworld.project.board.model.service.BoardTypeService;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Slf4j
public class BoardTypeController {

    @Autowired
    private BoardTypeService boardTypeService;

    /** 현재 회원의 게시판 목록 조회
     * @author Jiho
     * @param memberNo 현재 조회 중인 회원 번호
     * @return 게시판 목록
     */
    @GetMapping("{memberNo:[0-9]+}/board/select")
    public List<BoardType> selectBoardType(@PathVariable int memberNo, HttpSession session) {

        // 현재 회원의 게시판 목록을 조회해서 가져옴
        List<BoardType> boardTypeList = boardTypeService.selectBoardType(memberNo);

        // session scope에 boardTypeList 갱신
        session.setAttribute("boardTypeList", boardTypeList);

        return boardTypeList;
    }

    /**
     * @param memberNo 현재 회원번호
     * @param boardType 전달받은 게시판 제목, 권한(0, 1)
     * @return result 새로운 게시판 생성 성공 1, 실패 0
     */
    @PostMapping("{memberNo:[0-9]+}/board/insert")
    public int createBoardType(@PathVariable("memberNo") int memberNo,
                               @RequestBody BoardType boardType) {

        // 현재 회원번호 정보를 세팅
        boardType.setMemberNo(memberNo);

        return boardTypeService.insertBoardType(boardType);
    }

    @PutMapping("{memberNo:[0-9]+}/board/update")
    public int updateBoardType(@PathVariable("memberNo") int memberNo,
                               @RequestBody BoardType boardType) {

        int result = 1;
        return result;
    }

    @DeleteMapping("{memberNo:[0-9]+}/board/delete")
    public int deleteBoardType(@PathVariable("memberNo") int memberNo,
                               @RequestBody BoardType boardType) {

        // 현재 회원번호 정보를 세팅
        boardType.setMemberNo(memberNo);

        return boardTypeService.deleteBoardType(boardType);
    }
}
