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
	
	// 추가 필드
    private String fromMemberName;    // 작성자 이름
    private String fromMemberImg;     // 작성자 프로필 이미지
}
