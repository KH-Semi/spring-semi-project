package com.featherworld.project.miniHome.model.dto;

import org.springframework.web.multipart.MultipartFile;

public class ProfileDTO {
    private String memberId;
    private String nickname;
    private String bio;
    private String profileImage;
    
    private MultipartFile profileImageFile; // 업로드 파일 받기용

    // Getter / Setter
}
