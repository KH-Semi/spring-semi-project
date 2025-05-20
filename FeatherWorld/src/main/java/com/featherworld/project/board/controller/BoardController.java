package com.featherworld.project.board.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import com.featherworld.project.member.model.dto.Member;
import com.featherworld.project.board.model.dto.Board;
import com.featherworld.project.board.model.dto.BoardType;
import com.featherworld.project.board.model.service.BoardService;

@Controller
@Slf4j
public class BoardController {

	@Autowired
	private BoardService service;

	/** 해당 회원의 게시판 목록 조회해서 세션에 저장
	 * @param memberNo 현재 조회 중인 회원 번호
	 * @param session 세션 객체
	 * @param ra message 전달
	 */
	@GetMapping("{memberNo:[0-9]+}/board")
	public String prevBoardMainPage(@PathVariable("memberNo") int memberNo, HttpSession session,
									RedirectAttributes ra) {

		// 회원 번호 존재 유무 검사
		int result = service.checkMember(memberNo);

		if(result == 0) { // DB에 존재하지 않는 회원 번호인 경우 main page로 redirect
			ra.addFlashAttribute("message", "존재하지 않는 회원입니다.");
			return "redirect:/";
		}

		// 현재 회원의 게시판 종류 번호(boardCode) 목록을 조회해서 가져옴
		List<BoardType> boardTypeList = service.selectBoardType(memberNo);

		// session scope에 boardTypeList 저장
		session.setAttribute("boardTypeList", boardTypeList);

		return String.format("redirect:/%d/board/%d", memberNo, boardTypeList.getFirst().getBoardCode());
	}

	/** 해당 게시판의 삭제되지 않은 게시글 목록 조회
	 * @author Jiho
	 * @param memberNo 현재 조회 중인 회원 번호
	 * @param boardCode 해당 게시판 종류 번호
	 * @param cp 현재 페이지 번호
	 * @param boardTypeList 현재 조회 중인 회원의 게시판 목록 리스트
	 * @param model 게시글/페이징 목록 전달
	 * @param ra message 전달
	 */
	@GetMapping("{memberNo:[0-9]+}/board/{boardCode:[0-9]+}")
	public String boardMainPage(@PathVariable("memberNo") int memberNo, @PathVariable("boardCode") int boardCode,
								@SessionAttribute(value = "boardTypeList", required = false) List<BoardType> boardTypeList,
								@RequestParam(value = "cp", required = false) Integer cp,
								Model model, RedirectAttributes ra) {

		// 게시판 번호를 입력하여 직접적으로 접근할 때
		// 해당 회원의 게시판 목록부터 조회해서 session에 넣을 수 있도록 함
		if(boardTypeList == null || boardTypeList.isEmpty()) {
			return String.format("redirect:/%d/board", memberNo);
		}

		// boardCode가 현재 회원이 소유한 게시판 종류 번호인지 확인
		for(BoardType boardType : boardTypeList) {

			if(boardType.getBoardCode() == boardCode) {
				// 해당 게시판 종류 번호
				// boardList.js 에서 활용하기 위해 현재 게시판 종류 번호 선언
				model.addAttribute("currentBoardCode", boardCode);

				log.debug("회원 {}의 게시판 번호 {}", memberNo, boardCode);

				break;
			}

			ra.addFlashAttribute("message", "존재하지 않는 게시판입니다.");
			return "redirect:/";
		}

		// 해당 게시판의 게시글만 조회
		Map<String, Object> map = null;

		if(cp == null)	map = service.selectBoardList(boardCode, 1);
		else map = service.selectBoardList(boardCode, cp);

		log.debug("현재 페이지 : {}", cp);

		// request scope에 boardList, pagination 저장
		// (게시글이 없다면 각각 null 저장됨)
		model.addAttribute("boardList", map.get("boardList"));
		model.addAttribute("pagination", map.get("pagination"));
		
		// forward
		return "board/boardMain";
	}
	
	/** 비동기로 게시글 목록, 페이지 목록 반환
	 * @author Jiho
	 * @param boardCode 선택한 게시판 종류 번호
	 * @param cp 현재 페이지 번호
	 */
	@ResponseBody
	@GetMapping("board/{boardCode:[0-9]+}")
	public Map<String, Object> selectBoardList(@PathVariable("boardCode") int boardCode,
											  @RequestParam(value = "cp", required = false) Integer cp) {

		if(cp == null)	return service.selectBoardList(boardCode, 1);
		else return service.selectBoardList(boardCode, cp);
	}

	/** 게시글 쓰기
	 * @param memberNo 현재 회원 번호
	 * @param boardCode 현재 게시판 종류 번호
	 * @param cp 현재 페이지 번호
	 */
	@GetMapping("{memberNo:[0-9]+}/board/{boardCode:[0-9]+}/write")
	public String boardWrite(@PathVariable("memberNo") int memberNo, @PathVariable("boardCode") int boardCode,
							 @RequestParam(value = "cp", required = false) Integer cp) {


		if(cp == null)	cp = 1;

		return "board/boardWrite";
	}
	
	@GetMapping("{memberNo:[0-9]+}/board/{boardCode:[0-9]+}/{boardNo:[0-9]+}")
	public String boardDetail(@PathVariable("memberNo") int memberNo,
							  @PathVariable("boardCode") int boardCode,
							  @PathVariable("boardNo") int boardNo,
							  
							  Model model,
							  @SessionAttribute(value="loginMember", required = false) Member loginMember,
							  RedirectAttributes ra,
							  HttpServletRequest req,
							  HttpServletResponse resp) {
		
		// 게시글 상세 조회 서비스 호출
		
		// 1) Map으로 전달할 파라미터
		Map<String, Integer> map = new HashMap<>();
		map.put("boardCode", boardCode);
		map.put("boardNo", boardNo);
		
		
		if(loginMember != null) {
			map.put("memberNo", loginMember.getMemberNo());
		}	
			// 2) 서비스 호출
			Board board = service.selectOne(map);
			
			String path;
			
			
			// 조회결과가 없는 경우
			if(board == null) {
				ra.addFlashAttribute("message", "게시글이 존재하지 않습니다.");
				path = "redirect:/board/" + boardCode; // 게시글 목록으로 재요청
				
			} else {
				model.addAttribute("board", board);
				path = "board/boardDetail"; // boardDetail.html로 forward
			}
			
			return path;
			
		}

    
	/** 게시글 좋아요 체크/해제
	 * @author 허배령
	 */
	@ResponseBody
	@PostMapping("like") // /board/like (POST)
	public int boardLike(@RequestBody Map<String, Integer> map) {
		return service.boardLike(map);
	}
}
