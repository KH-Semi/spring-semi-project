package com.featherworld.project.friend.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class IlchonComment {
	private int toMemberNo;
	private int fromMemberNo;
	private String ilchonCommentContent;
	
}
