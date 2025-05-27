package com.featherworld.project.miniHome.model.service;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.featherworld.project.board.model.dto.Board;
import com.featherworld.project.friend.model.dto.Ilchon;

import com.featherworld.project.member.model.dto.Member;
import com.featherworld.project.member.model.dto.Today;
import com.featherworld.project.miniHome.model.dto.MiniHomeRecentBoard; // ★ 추가된 import

public interface MiniHomeService {




    /** 일촌평 리스트 가져오기 */
    List<Ilchon> getIlchonComments(int memberNo);

    /** 최근 게시글 리스트 가져오기 */
    List<Board> getRecentBoards(int memberNo);
    


	/** 미니홈주인 찾아서 Member타입 반환
	 * @param memberNo
	 * @return
	 * @author 영민
	 */
	Member findmember(int memberNo);

	/** 일촌인지 아닌지확인
	 * @param friend
	 * @return
	 */
	int findilchon(Ilchon friend);

	/** 방문홈피(memberNo) 의 로그인회원이있다면 투데이 insert
	 * @param today
	 * @return
	 */
	int todayAdd(Today today);

	/** memberNo의 투데이값을 가져옴
	 * @param today
	 * @return
	 */
	int todayCount(Today today);

	/** 오늘 방문했는지 확인
	 * @param today
	 * @return
	 */
	int todayConfirm(Today today);

	/** memberNo의 홈피방문자 총집계
	 * @param memberNo
	 * @return
	 */
	int totalCount(int memberNo);

	/** 팔로워수 카운팅
	 * @param memberNo
	 * @return
	 */
	int getFollowerCount(int memberNo);

	/** 팔로윙 수 카운팅
	 * @param memberNo
	 * @return
	 */
	int getFollowingCount(int memberNo);

	/** 미수락된 팔로워수 카운팅 (memberNo == loginMember.getMemberNo) 일떄만 나오게끔
	 * @param memberNo
	 * @return
	 */
	int getPendingFollowerCount(int memberNo);

	/** 일촌신청 하기
	 * @param followRequest
	 * @return
	 */
	int sendFollowRequest(Ilchon followRequest);

	/** 일촌신청중인 N값있는지확인
	 * @param theirRequest
	 * @return
	 */
	int findPendingIlchon(Ilchon theirRequest);

	/** 게시판 총개수
	 * @param memberNo
	 * @return
	 */
	int getTotalBoardCount(int memberNo);

	/** 방명록 총갯수
	 * @param memberNo
	 * @return
	 */
	int getTotalGuestBookCount(int memberNo);



	/** fromComment 삭제
	 * @param ilchonRelation
	 * @return
	 */
	int deleteIlchonFromComment(Ilchon ilchonRelation);

	/** tocomment 삭제
	 * @param ilchonRelation
	 * @return
	 */
	int deleteIlchonToComment(Ilchon ilchonRelation);

	int updateIlchonFromComment(Ilchon ilchonRelation);

	int updateIlchonToComment(Ilchon reverseRelation);

	int findIlchon(Ilchon friend);

	

	
}
