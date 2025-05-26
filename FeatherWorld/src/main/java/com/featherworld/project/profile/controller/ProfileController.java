package com.featherworld.project.profile.controller;
import java.io.File;
import java.io.IOException;

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
    public String viewProfile(@PathVariable int memberNo, Model model) {
        Profile profile = profileService.selectProfile(memberNo);
        model.addAttribute("profile", profile);
        return "profile/profile";
    }

    // 프로필 수정 화면
    @GetMapping("{memberNo:[0-9]+}/editprofile")
    public String editProfilePage(@PathVariable int memberNo, Model model) {
        Profile profile = profileService.selectProfile(memberNo);
        model.addAttribute("profile", profile);
        return "profile/editprofile";
    }

    // 프로필 업데이트
    @PostMapping("{memberNo:[0-9]+}/profileupdate")
    public String updateProfile(@PathVariable int memberNo,
                                 @RequestParam("uploadFile") MultipartFile uploadFile,
                                 @RequestParam("bio") String bio) throws IOException {

        String filePath = null;

        if (!uploadFile.isEmpty()) {
            filePath = "/myPage/profile/" + uploadFile.getOriginalFilename();
            uploadFile.transferTo(new File("C:/uploadFiles/profile/" + uploadFile.getOriginalFilename()));
        }

        Profile profile = new Profile();
        profile.setMemberNo(memberNo);
        profile.setBio(bio);
        profile.setProfileImg(filePath);

        profileService.saveOrUpdateProfile(profile);
        return "redirect:/" + memberNo + "/profile";
    }
}
