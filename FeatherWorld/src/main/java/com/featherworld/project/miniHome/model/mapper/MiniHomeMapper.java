package com.featherworld.project.miniHome.model.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.featherworld.project.board.model.dto.Board;
import com.featherworld.project.friend.model.dto.Ilchon;
import com.featherworld.project.friend.model.dto.IlchonComment;
import com.featherworld.project.member.model.dto.Member;
import com.featherworld.project.miniHome.model.dto.MiniHomeRecentBoard;
import com.featherworld.project.member.model.dto.Today;

@Mapper
public interface MiniHomeMapper {
    
	Member findmember(int memberNo);

    int findilchon(Ilchon friend);

    List<IlchonComment> getIlchonComments(int memberNo);
// ---------------------------------------  체크 ..
	int todayAdd(Today today);
	int todayCount(Today today);

	int todayconfirm(Today today);

	int totalCount(int memberNo);

	int todayConfirm(Today today);

	int getFollowerCount(int memberNo);

	int getFollowingCount(int memberNo);

	int getPendingFollowerCount(int memberNo);

	int sendFollowRequest(Ilchon followRequest);

	int findPendingIlchon(Ilchon theirRequest);

	int findAcceptedIlchon(Ilchon myRequest);
	
	List<Board> getRecentBoards(int memberNo);
	
	int getTotalBoardCount(int memberNo);

	int getTotalGuestBookCount(int memberNo);


}
