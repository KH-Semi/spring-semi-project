package com.featherworld.project.board.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BoardImg {

	private int imgNo;
	private String imgPath;
	private String imgOriginalName;
	private String imgRename;
	private int imgOrder;
	private int boardNo;
}
