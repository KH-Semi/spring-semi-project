package com.featherworld.project.miniHome.model.mapper;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface FollowMapper {
    int countFollowers(int memberNo);
    int countFollowing(int memberNo);
}
