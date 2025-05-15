package com.featherworld.project.board.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.featherworld.project.board.model.dto.BoardType;
import com.featherworld.project.board.model.service.BoardService;

import lombok.extern.slf4j.Slf4j;

@Controller
@RequestMapping("board")
@Slf4j
public class BoardController {

	@Autowired
	BoardService service;
	
	/** 선택된 게시판에 해당하는 게시글 조회 (게시판 종류 - 비동기)
	 * @author Jiho
	 * @param memberNo : 현재 조회 중인 회원 번호
	 * @param boardCode : 현재 선택된 게시판 종류 번호
	 * @param model
	 * @return
	 */
	@GetMapping("{memberNo:[0-9]+}")
	public String boardMainPage(@PathVariable("memberNo") int memberNo,
								@RequestBody(required = false) BoardType boardType,
								@RequestParam(value = "cp", required = false, defaultValue = "1") int cp,
								Model model, RedirectAttributes ra) {
		
		// 0. 회원 번호 유효성 검사
		int result = service.checkMember(memberNo);
		
		if(result == 0) { // 존재하지 않는 회원 번호인 경우 main page로 redirect
			
			ra.addFlashAttribute("message", "존재하지 않는 회원입니다.");
			
			return "redirect:/";
		}
		
		// 1. 현재 회원의 게시판 종류 번호(boardCode)를 조회해서 가져옴
		List<BoardType> boardTypeList = service.selectBoardType(memberNo);
		
		// request scope에 boardTypeList 저장
		model.addAttribute("boardTypeList", boardTypeList);
		
		// 2. 게시판 선택
		int currentBoardCode = 0;
		
		if(boardType == null) { // 게시판이 클릭되지 않은 경우(boardCode = null)
			
			// 가장 처음 생성된 default 게시판 조회
			currentBoardCode = boardTypeList.getFirst().getBoardCode();
			
		} else {
			
			// 게시판이 클릭된 경우(boardCode = 해당 게시판 종류 번호)	
			currentBoardCode = boardType.getBoardCode();
		}
		
		log.debug("현재 게시판 번호 : {}", currentBoardCode);
		
		// 3. 해당 게시판의 게시글만 조회
		Map<String, Object> map = service.selectBoardList(currentBoardCode, cp);
		
		// request scope에 boardList, paginatioin 저장
		// (게시글이 없다면 각각 null 저장됨)
		model.addAttribute("boardList", map.get("boardList"));
		model.addAttribute("pagination", map.get("pagination"));
		
		// forward
		return "board/boardList";
	}
	
	@GetMapping("{memberNo:[0-9]+}/write")
	public String boardWrite(@PathVariable("memberNo") int memberNo,
							 @RequestParam(value = "cp", required = false, defaultValue = "1") int cp) {
		
		
		
		return "board/boardWrite";
	}
}
