package com.featherworld.project.board.model.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.featherworld.project.board.model.dto.Board;
import com.featherworld.project.board.model.dto.BoardType;

@Mapper
public interface BoardMapper {

	int checkMember(int memberNo);

	List<BoardType> selectBoardType(int memberNo);

	List<Board> selectBoardList(int boardCode);

	
}
