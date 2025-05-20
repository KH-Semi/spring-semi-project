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

    @PostMapping()
    public int createBoardType(@RequestBody BoardType boardType) {

        int result = 1;
        return result;
    }

    @PutMapping()
    public int updateBoardType(@RequestBody BoardType boardType) {

        int result = 1;
        return result;
    }

    @DeleteMapping()
    public int deleteBoardType(@RequestBody BoardType boardType) {

        int result = 1;
        return result;
    }
}
