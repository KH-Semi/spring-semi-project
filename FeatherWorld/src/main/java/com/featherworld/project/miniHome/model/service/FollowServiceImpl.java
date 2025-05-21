package com.featherworld.project.miniHome.model.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.featherworld.project.miniHome.model.mapper.FollowMapper;

@Service
public class FollowServiceImpl implements FollowService {

    @Autowired
    private FollowMapper followMapper;

    @Override
    public int countFollowers(int memberNo) {
        return followMapper.countFollowers(memberNo);
    }

    @Override
    public int countFollowing(int memberNo) {
        return followMapper.countFollowing(memberNo);
    }
}
