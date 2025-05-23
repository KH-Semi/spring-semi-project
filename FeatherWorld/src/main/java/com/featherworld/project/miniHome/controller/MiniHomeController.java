package com.featherworld.project.miniHome.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.featherworld.project.friend.model.dto.Ilchon;
import com.featherworld.project.member.model.dto.Member;

import com.featherworld.project.miniHome.model.service.MiniHomeService;



@Controller
public class MiniHomeController {

    @Autowired
    private MiniHomeService miniHomeService;
    


    
    @GetMapping("{memberNo:[0-9]+}/minihome")
    public String miniHome(@PathVariable("memberNo") int memberNo ,
    		               @SessionAttribute(value ="loginMember", required=false) Member loginMember ,
    		               Model model) {
    	
       Member member = miniHomeService.findmember(memberNo);
       
       model.addAttribute("member",member);
       
       //일촌 관계 초기값
       boolean isIlchon = false;
       if(loginMember != null && loginMember.getMemberNo() != memberNo) {
    	   Ilchon friend = new Ilchon();
    	   friend.setFromMemberNo(loginMember.getMemberNo());
    	   friend.setToMemberNo(memberNo);
    	   
    	   int count = miniHomeService.findilchon(friend);
    	   // 둘이 일촌관계인지아닌지좀 확인 ..
    	   
    	   if(count >= 1) {
    		   isIlchon = true;
    	   }else {
    		   isIlchon = false;
    	   }
    	   model.addAttribute("isIlchon",isIlchon);
       }
    	
    	
    	return "miniHome/miniHome";
    }
    
    @PostMapping("{memberNo:[0-9]+}/prifleupdate")
    public String fileUpload1(@RequestParam("uploadFile") MultipartFile uploadFile,
            RedirectAttributes ra) throws Exception {

        String path = miniHomeService.fileUpload1(uploadFile);

        if (path != null) {
            ra.addFlashAttribute("path", path);
        }

        return "profile/profileupdate";
    }

    /** 업로드한 파일 DB 저장 + 서버 저장 + 조회
     * @param uploadFile
     * @return
     */
    @PostMapping("{memberNo:[0-9]+}/minihome")
    public String fileUpload2(@RequestParam("uploadFile") MultipartFile uploadFile,
            @SessionAttribute("loginMember") Member loginMember,
            @PathVariable("memberNo") int memberNo,
            RedirectAttributes ra) throws Exception {

        // 로그인한 회원 번호
        int loginMemberNo = loginMember.getMemberNo();

        // 업로드된 파일 정보를 db로
        int result = miniHomeService.fileUpload2(uploadFile, loginMemberNo);

        return "redirect:/" + memberNo + "/minihome";
    }
}