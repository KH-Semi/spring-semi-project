package com.featherworld.project.profile.controller;
import java.io.File;
import java.io.IOException;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import com.featherworld.project.profile.model.dto.Profile;

 

import com.featherworld.project.profile.model.service.ProfileService;

@Controller
public class ProfileController {

    @Autowired
    private ProfileService profileService;

    // 프로필 보기
    @GetMapping("{memberNo:[0-9]+}/profile")
    public String Profile(@PathVariable("memberNo") int memberNo, Model model) {
        Profile profile = profileService.selectProfile(memberNo);
        model.addAttribute("profile", profile);
        model.addAttribute("memberNo", memberNo); // memberNo도 추가
        return "profile/profile";
    }


    @GetMapping("{memberNo:[0-9]+}/editprofile")
    public String editProfilePage(@PathVariable("memberNo") int memberNo, Model model) {
        Profile profile = profileService.selectProfile(memberNo);
        model.addAttribute("profile", profile);
        model.addAttribute("memberNo", memberNo);  // 여기를 추가!
        return "profile/editprofile";
    }

    

    // 프로필 업데이트
    @GetMapping("{memberNo:[0-9]+}/profileupdate")
    public String showProfileUpdateForm(@PathVariable("memberNo") int memberNo, Model model) {
        Profile profile = profileService.selectProfile(memberNo);
        model.addAttribute("profile", profile);
        return "profile/profileupdate";
    }
    
    @PostMapping("{memberNo:[0-9]+}/profileupdate")
    public String updateProfile(@PathVariable("memberNo") int memberNo,
                                @RequestParam("uploadFile") MultipartFile uploadFile,
                                @RequestParam("bio") String bio) throws IOException {

        Profile profile = profileService.selectProfile(memberNo); // 기존 프로필 가져오기

     // 기존 파일명 저장용 변수
        String originalFilename = null;
        String renameFilename = null;
        String filePath = null;

        if (!uploadFile.isEmpty()) {
            originalFilename = uploadFile.getOriginalFilename();

            // 확장자 추출
            String ext = "";
            int dotIndex = originalFilename.lastIndexOf('.');
            if (dotIndex >= 0) {
                ext = originalFilename.substring(dotIndex);
            }

            renameFilename = UUID.randomUUID().toString() + ext;

            String saveDir = "C:/uploadFiles/profile/";
            File saveFile = new File(saveDir + renameFilename);
            uploadFile.transferTo(saveFile);

            filePath = "/myPage/profile/" + renameFilename;
        }

        // 프로필 객체 생성 또는 수정
        if (profile == null) {
            profile = new Profile();
            profile.setMemberNo(memberNo);
        }

        profile.setImgPath(filePath);
        profile.setImgOriginalName(originalFilename);
        profile.setImgRename(renameFilename);
        profile.setProfileContent(bio);

        // DB 저장 또는 업데이트
        profileService.saveOrUpdateProfile(profile);

        return "redirect:/" + memberNo + "/profile";
    }


}
