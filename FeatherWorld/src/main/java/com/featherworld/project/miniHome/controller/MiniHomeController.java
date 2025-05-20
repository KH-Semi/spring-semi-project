package com.featherworld.project.miniHome.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class MiniHomeController {

    @GetMapping("{memberNo:[0-9]+}/minihome")
    public String miniHome(@PathVariable("memberNo") int memberNo, Model model) {
    	model.addAttribute("memberNo", memberNo);  // memberNo 추가
        return "miniHome/miniHome";
    }
}
