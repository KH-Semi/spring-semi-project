package com.featherworld.project.board.model.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Board {
	
	private int boardNo;
	private String boardTitle;
	private String boardContent;
	private String boardWriteDate;
	private String boardUpdateDate;
	private int readCount;
	private char boardDelFl;
	private int boardCode;
	private int memberNo;
	
	private int likeCount;
	
	private String thumbnail;
	
	private List<BoardImg> boardImgList;
	
	private int likeCheck;
}
