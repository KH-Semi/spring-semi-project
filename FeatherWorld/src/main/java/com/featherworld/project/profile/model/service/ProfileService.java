package com.featherworld.project.profile.model.service;

import com.featherworld.project.profile.model.dto.Profile;

public interface ProfileService {
    Profile selectProfile(int memberNo);
    int saveOrUpdateProfile(Profile profile);
}
