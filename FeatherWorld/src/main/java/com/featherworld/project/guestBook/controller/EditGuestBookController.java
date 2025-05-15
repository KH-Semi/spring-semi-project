package com.featherworld.project.guestBook.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.featherworld.project.guestBook.service.EditGuestBookService;
import com.featherworld.project.guestBook.service.GuestBookService;

import lombok.extern.slf4j.Slf4j;

@Controller
@RequestMapping("editGuestBook")
@Slf4j
public class EditGuestBookController {

	@Autowired
	private EditGuestBookService service;
	
	
	@Autowired
	private GuestBookService guestBookService;
	
	
	
	
	
	
	
}
