package com.featherworld.project.board.controller;

import com.featherworld.project.board.model.dto.BoardType;
import com.featherworld.project.board.model.service.BoardService;
import com.featherworld.project.board.model.service.BoardTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class BoardTypeController {

    @Autowired
    private BoardTypeService boardTypeService;

    @PostMapping("{memberNo:[0-9]+}/board/insert")
    public int createBoardType(@PathVariable("memberNo") int memberNo,
                               @RequestBody BoardType boardType) {



        return boardTypeService.insertBoardType(memberNo);
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

        int result = 1;
        return result;
    }
}
