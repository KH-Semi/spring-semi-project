package com.featherworld.project.guestBook.model.dto;

import com.featherworld.project.member.model.dto.Member;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GuestBook {

	//GuestBook 테이블 컬럼
	private int guestBookNo;
	private String guestBookContent;
	private String guestBookWriteDate;
	private int ownerNo;
	private int visitorNo;
	private Member visitor;
	
	public Member getVisitor() {
	    return visitor;
	}

	public void setVisitor(Member visitor) {
	    this.visitor = visitor;
	}
}
