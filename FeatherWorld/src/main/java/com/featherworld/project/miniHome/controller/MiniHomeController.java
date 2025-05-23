package com.featherworld.project.miniHome.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.SessionAttribute;

import com.featherworld.project.member.model.dto.Member;

import com.featherworld.project.miniHome.model.service.MiniHomeService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.featherworld.project.member.model.dto.Member;
import com.featherworld.project.miniHome.model.service.MiniHomeService;

import ch.qos.logback.core.model.Model;
import jakarta.servlet.http.HttpSession;

@Controller
public class MiniHomeController {

    @Autowired
    private MiniHomeService miniHomeService;
    
    @GetMapping("{memberNo:[0-9]+}/minihome")
    public String miniHome(@PathVariable("memberNo") int memberNo ,
    		               @SessionAttribute(value ="loginMember", required=false) Member loginMember ,
    		               Model model) {
       
       model.addAttribute("member",memberNo);
 
    	
    	
    	return "miniHome/miniHome";
    }
    


   
}
