package com.featherworld.project.myPage.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MyPageController {

	@GetMapping("{memberNo:[0-9]+}/minihome")
	public String miniHome() {
		
		return"miniHome/miniHome";
	}
	
}
