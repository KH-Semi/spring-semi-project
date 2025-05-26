package com.featherworld.project.miniHome.model.mapper;

import org.apache.ibatis.annotations.Mapper;

import com.featherworld.project.friend.model.dto.Ilchon;
import com.featherworld.project.member.model.dto.Member;
import com.featherworld.project.member.model.dto.Today;

@Mapper
public interface MiniHomeMapper {

	

	Member findmember(int memberNo);

	int findilchon(Ilchon friend);

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
