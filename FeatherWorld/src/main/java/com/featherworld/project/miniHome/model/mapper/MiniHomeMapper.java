package com.featherworld.project.miniHome.model.mapper;

import java.util.List;

import com.featherworld.project.friend.model.dto.IlchonComment;
import com.featherworld.project.member.model.dto.Member;
import com.featherworld.project.miniHome.model.dto.MiniHomeRecentBoard;

public interface MiniHomeMapper {
    Member findmember(int memberNo);

    int findilchon(com.featherworld.project.friend.model.dto.Ilchon friend);

    List<MiniHomeRecentBoard> selectRecentBoards(int memberNo);

    List<IlchonComment> selectIlchonComments(int memberNo);
}
