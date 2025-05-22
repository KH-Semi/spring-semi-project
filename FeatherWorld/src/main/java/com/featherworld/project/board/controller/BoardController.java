package com.featherworld.project.board.controller;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import com.featherworld.project.board.model.dto.Board;
import com.featherworld.project.board.model.dto.BoardImg;
import com.featherworld.project.board.model.dto.BoardType;
import com.featherworld.project.board.model.service.BoardService;
import com.featherworld.project.board.model.service.BoardTypeService;
import com.featherworld.project.member.model.dto.Member;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@Slf4j
public class BoardController {

	@Autowired
	private BoardService service;

	@Autowired
	private BoardTypeService boardTypeService;

	/** 해당 회원의 게시판 목록 조회해서 세션에 저장
	 * @param memberNo 현재 조회 중인 회원 번호
	 * @param session 세션 객체
	 * @param ra message 전달
	 */
	@GetMapping("{memberNo:[0-9]+}/board")
	public String prevBoardMainPage(@PathVariable("memberNo") int memberNo, HttpSession session,
									RedirectAttributes ra) {

		// 현재 회원의 게시판 종류 번호(boardCode) 목록을 조회해서 가져옴
		List<BoardType> boardTypeList = boardTypeService.selectBoardType(memberNo);

		// session scope에 boardTypeList 저장
		session.setAttribute("boardTypeList", boardTypeList);

		return String.format("redirect:/%d/board/%d", memberNo, boardTypeList.getFirst().getBoardCode());
	}

	/** 해당 게시판의 삭제되지 않은 게시글 목록 조회
	 * @author Jiho
	 * @param memberNo 현재 조회 중인 회원 번호
	 * @param boardCode 해당 게시판 종류 번호
	 * @param cp 현재 페이지 번호
	 * @param model 게시글/페이징 목록 전달
	 * @param ra message 전달
	 */
	@GetMapping("{memberNo:[0-9]+}/board/{boardCode:[0-9]+}")
	public String boardMainPage(@PathVariable("memberNo") int memberNo, @PathVariable("boardCode") int boardCode,
								@RequestParam(value = "cp", required = false) Integer cp, HttpSession session,
								Model model, RedirectAttributes ra) {

		// 현재 회원의 게시판 종류 번호(boardCode) 목록을 조회해서 가져옴
		List<BoardType> boardTypeList = boardTypeService.selectBoardType(memberNo);

		// session scope에 boardTypeList 저장
		session.setAttribute("boardTypeList", boardTypeList);

		boolean isValid = false;
		// boardCode가 현재 회원이 소유한 게시판 종류 번호인지 확인
		for(BoardType boardType : boardTypeList) {

         if(boardType.getBoardCode() == boardCode) {
            // 해당 게시판 종류 번호
            // boardList.js 에서 활용하기 위해 현재 게시판 종류 번호 선언
            model.addAttribute("currentBoardCode", boardCode);

			isValid = true;
			break;
			}
		}

		if(!isValid) {
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

   /** 게시글 상세 조회
    * @author 허배령
	 * @param memberNo
	 * @param boardCode
	 * @param boardNo
	 * @param model
	 * @param loginMember
	 * @param ra
	 * @param req
	 * @param resp
	 * @return
	 */
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

			// 1) Map으로 전달할 파라미터 묶기
			Map<String, Integer> map = new HashMap<>();
			map.put("boardCode", boardCode);
			map.put("boardNo", boardNo);

			// 로그인 상태인 경우에만 memberNo 추가
			if(loginMember != null) {
				map.put("memberNo", loginMember.getMemberNo());

			}

			// 2) 서비스 호출
			Board board = service.selectOne(map);
			
			String path = null;
			
			// 조회결과가 없는 경우
			if(board == null) {
				path = "redirect:/board/" + boardCode; // 목록 재요청
				ra.addFlashAttribute("message", "게시글이 존재하지 않습니다.");
				
			} else {

				/* --------- 쿠키를 이용한 조회 수 증가 (시작) --------------- */

				// 비회원 또는 로그인 한 회원이 아닌 경우 (== 글쓴이를 뺀 다른 사람)
				if(loginMember == null || loginMember.getMemberNo() != board.getMemberNo()) {

					// 요청에 담겨있는 모든 쿠키 얻어오기
					Cookie[] cookies = req.getCookies();

					Cookie c = null;

					for(Cookie temp : cookies) { // readBoardNo 존재할 때 이 클라이언트가
												 // 어떤 게시글을 이미 읽은 이력이 있다
						// 요청에 담긴 쿠키에 "readBoardNo"가 존재할 때
						if(temp.getName().equals("readBoardNo")) {
							c = temp;
							break;
						}
					}

					int result = 0; // 조회수 증가 결과를 저장할 변수

					// "readBoardNo"가 쿠키에 없을 때
					if(c == null) {

						// 새 쿠키 생성 ("readBoardNo", [게시글 번호])
						c = new Cookie("readBoardNo", "[" + boardNo + "]");
						result = service.updateReadCount(boardNo);


					} else {
						// readBoardNo"가 쿠키에 있을 때
						// "readBoardNo" : [2][30][400]

						// 현재 게시글을 처음 읽는 경우
						if(c.getValue().indexOf("[" + boardNo + "]") == -1) {

							// 해당 글 번호를 쿠키에 누적 + 서비스 호출(조회수 증가)
							c.setValue(c.getValue() + "[" + boardNo + "]");
							result = service.updateReadCount(boardNo);
						}

					}

					// 조회 수 증가 성공 / 조회 성공 시
					if(result > 0 ) {

						// 먼저 조회 된 board의 readCount 값을
						// result 값으로 다시 세팅
						board.setReadCount(result);

						// 쿠키 적용 경로 설정
							// "/" 이하 경로 요청 시 쿠키 서버로 전달
						c.setPath("/"); // "/" 이하 경로 요청 시 쿠키 서버로 전달

						// 쿠키 수명 지정
						// 현재 시간을 얻어오기
						LocalDateTime now = LocalDateTime.now();

						// 다음날 자정 지정
						LocalDateTime nextDayMidnight = now.plusDays(1)
														   .withHour(0)
														   .withMinute(0)
														   .withSecond(0)
														   .withNano(0);
						// 현재시간부터 다음날 자정까지 남은 시간 계산 (초단위)
						long seconds = Duration.between(now, nextDayMidnight).getSeconds();

						// 쿠키 수명 설정
						c.setMaxAge((int)seconds);

						resp.addCookie(c); // 응답 객체를 이용해서 클라이언트에게 쿠키 전달

					}
				}

				/* --------- 쿠키를 이용한 조회 수 증가 (끝) --------------- */

				// 조회결과가 있는 경우
				path = "board/boardDetail"; // boardDetail.html로 forward

				// imageList가 null인 경우 빈 리스트로 초기화
				if(board.getImageList() == null) {
				    board.setImageList(new ArrayList<>());
				}

				// board - 게시글 일반 내용 + imageList + commentList
				model.addAttribute("board", board);

				// 조회된 이미지 목록(imageList)이 있을 경우
				if(!board.getImageList().isEmpty()) {

				    BoardImg thumbnail = null;

				    // imageList의 0번 인덱스 == 가장 빠른 순서(imgOrder)
				    // 만약 이미지 목록의 첫번째 행의 imgOrder가 0 == 썸네일인 경우
				    if(board.getImageList().get(0).getImgOrder() == 0) {

				        thumbnail = board.getImageList().get(0);
				    }

				    model.addAttribute("thumbnail", thumbnail);
				    model.addAttribute("start", thumbnail != null ? 1 : 0);
				                    // start : 썸네일이 있다면 1, 없다면 0을 저장
				}
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
