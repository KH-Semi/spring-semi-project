package com.featherworld.project.miniHome.model.mapper;

import java.util.List;

import com.featherworld.project.friend.model.dto.IlchonComment;
import com.featherworld.project.member.model.dto.Member;
import com.featherworld.project.miniHome.model.dto.MiniHomeRecentBoard;
import com.featherworld.project.member.model.dto.Today;


public interface MiniHomeMapper {
    Member findmember(int memberNo);

    int findilchon(com.featherworld.project.friend.model.dto.Ilchon friend);

	

	Member findmember(int memberNo);


    List<MiniHomeRecentBoard> selectRecentBoards(int memberNo);


    List<IlchonComment> selectIlchonComments(int memberNo);

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


}
