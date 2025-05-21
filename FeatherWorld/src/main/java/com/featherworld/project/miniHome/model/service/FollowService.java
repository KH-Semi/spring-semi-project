package com.featherworld.project.miniHome.model.service;

import org.springframework.stereotype.Service;

@Service
public interface FollowService {
    int countFollowers(int memberNo);
    int countFollowing(int memberNo);
}