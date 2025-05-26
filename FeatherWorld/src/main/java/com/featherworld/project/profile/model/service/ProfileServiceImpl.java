package com.featherworld.project.profile.model.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.featherworld.project.profile.model.dto.Profile;
import com.featherworld.project.profile.model.mapper.ProfileMapper;

@Service
public class ProfileServiceImpl implements ProfileService {

    @Autowired
    private ProfileMapper mapper;

    @Override
    public Profile selectProfile(int memberNo) {
        return mapper.selectProfile(memberNo);
    }

    @Override
    public int saveOrUpdateProfile(Profile profile) {
        Profile existing = mapper.selectProfile(profile.getMemberNo());
        if (existing != null) {
            return mapper.updateProfile(profile);
        } else {
            return mapper.insertProfile(profile);
        }
    }
}
