package com.featherworld.project.profile.model.service;

import org.springframework.web.multipart.MultipartFile;

import com.featherworld.project.profile.model.dto.Profile;

public interface ProfileService {
    
	Profile selectProfile(int memberNo);
   
	int saveOrUpdateProfile(int loginMemberNo, MultipartFile uploadFile, String bio) throws Exception;
	
	int secession(String memberPw, int memberNo);
}
