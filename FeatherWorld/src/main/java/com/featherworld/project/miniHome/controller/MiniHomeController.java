package com.featherworld.project.miniHome.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.SessionAttribute;

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
       
       model.addAttribute("member",memberNo);
 
    	
    	
    	return "miniHome/miniHome";
    }
    


   
}