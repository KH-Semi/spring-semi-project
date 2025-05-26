package com.featherworld.project.profile.model.mapper;

import org.apache.ibatis.annotations.Mapper;

import com.featherworld.project.profile.model.dto.Profile;

@Mapper
public interface ProfileMapper {
    Profile selectProfile(int memberNo);
    int insertProfile(Profile profile);
    int updateProfile(Profile profile);
    int profileExists(int memberNo); // 사용하지 않으면 삭제해도 무방
}
