package com.featherworld.project.board.controller;

import com.featherworld.project.board.model.dto.BoardType;
import com.featherworld.project.board.model.service.BoardService;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@Controller
@Slf4j
@RequestMapping("board")
public class BoardController {

	@Autowired
	private BoardService service;
	
	/**
	 * 1. 해당 회원의 게시판 목록 조회
	 * 2. 해당 게시판의 삭제되지 않은 게시글 목록 조회
	 * 
	 * @author Jiho
	 * @param memberNo : 현재 조회 중인 회원 번호
	 * @param cp : 현재 페이지 번호
	 * @param session : 세션 객체
	 * @param model
	 * @return
	 */
	@GetMapping("{memberNo:[0-9]+}")
	public String boardMainPage(@PathVariable int memberNo,
								@RequestParam(value = "cp", required = false, defaultValue = "1") int cp,
								HttpSession session, Model model) {

		// 0. 회원 번호 존재 유무 검사
		int result = service.checkMember(memberNo);
		
		if(result == 0) { // DB에 존재하지 않는 회원 번호인 경우 main page로 redirect
			return "redirect:/";
		}
		
		// 1. 현재 회원의 게시판 종류 번호(boardCode) 목록을 조회해서 가져옴
		List<BoardType> boardTypeList = service.selectBoardType(memberNo);
		
		// session scope에 boardTypeList 저장
		session.setAttribute("boardTypeList", boardTypeList);
		
		// 2. 가장 처음 생성된 default 게시판 종류 번호
		int currentBoardCode = boardTypeList.getFirst().getBoardCode();
		// boardList.js에서 현재 선택된 게시판인지 확인하기 위해 전달
		// model.addAttribute("currentBoardCode", currentBoardCode);

		log.debug("default 게시판 번호 : {}", currentBoardCode);
		
		// 3. 해당 게시판의 게시글만 조회
		Map<String, Object> map = service.selectBoardList(currentBoardCode, cp);
		
		// request scope에 boardList, paginatioin 저장
		// (게시글이 없다면 각각 null 저장됨)
		model.addAttribute("boardList", map.get("boardList"));
		model.addAttribute("pagination", map.get("pagination"));
		
		// forward
		return "board/boardList";
	}
	
	/** 비동기로 게시글 목록, 페이지 목록 반환
	 * @author Jiho
	 * @param boardCode : 선택한 게시판 종류 번호
	 * @param cp : 현재 페이지 번호
	 * @return
	 */
	@ResponseBody
	@GetMapping("type/{boardCode:[0-9]+}")
	public Map<String, Object> selectBordList(@PathVariable int boardCode,
											  @RequestParam(value = "cp", required = false, defaultValue = "1") int cp) {
		return service.selectBoardList(boardCode, cp);
	}
	
	@GetMapping("type/{boardCode:[0-9]+}/write")
	public String boardWrite(@PathVariable int boardCode,
							 @RequestParam(value = "cp", required = false, defaultValue = "1") int cp) {
		

		return "board/boardWrite";
	}
    
	/** 게시글 좋아요 체크/해제
	 * @return
	 */
	@ResponseBody
	@PostMapping("like") // /board/like (POST)
	public int boardLike(@RequestBody Map<String, Integer> map) {
		return service.boardLike(map);
		
	}
}
