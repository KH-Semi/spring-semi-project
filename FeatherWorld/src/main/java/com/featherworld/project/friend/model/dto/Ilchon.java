package com.featherworld.project.friend.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Ilchon {
	
	// Member 어트리뷰트로부터 얻어오는 변수
	private String memberImg;
	private String memberName;
	
		
		
	private int toMemberNo;
	private int fromMemberNo;
	private String fromNickname;
	private String toNickname;
	private boolean isIlchon;
	
}
