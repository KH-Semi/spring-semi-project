package com.featherworld.project.main.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

/** 메인 페이지 컨트롤러
 * @author Jiho
 */
@Controller
public class MainController {
	
	/** 메인 페이지 forward 메서드
	 * @author Jiho
	 * @return src/main/resources/templates/common/main.html
	 */
	@RequestMapping("/")
	public String mainPage() {
		return "common/main";
	}
	@RequestMapping("{memberNo:[0-9]+}")
	public String memberPage(@PathVariable("memberNo") int memberNo) {
		
		return "redirect:/"+ memberNo +"/minihome";
	}
	
}
