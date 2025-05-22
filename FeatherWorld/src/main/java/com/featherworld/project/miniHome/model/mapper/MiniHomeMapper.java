package com.featherworld.project.miniHome.model.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.featherworld.project.member.model.dto.Member;

@Mapper
public interface MiniHomeMapper {

    Member selectMemberByNo(@Param("memberNo") int memberNo);

    int insertImage(@Param("memberNo") int memberNo,
                    @Param("originalName") String originalName,
                    @Param("renamed") String renamed,
                    @Param("type") String type);
}
