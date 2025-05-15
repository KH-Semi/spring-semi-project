package com.featherworld.project.board.controller;

import java.util.List;
import java.util.Map;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.SessionAttribute;

import com.featherworld.project.board.model.dto.Board;
import com.featherworld.project.board.model.service.BoardService;
import com.featherworld.project.member.model.dto.Member;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("board")
@RequiredArgsConstructor
public class BoardController {

    private final BoardService service;

    /** 폴더 목록 조회
     * @author 허배
     * @param memberNo
     * @return
     */
    @GetMapping("/{memberNo}")
    public List<Board> selectBoardTypeList(@PathVariable int memberNo) {
        return service.selectBoardTypeList(memberNo);
    }

    /** 폴더 생성
     * @author 허배
     * @param board
     * @param loginMember
     * @return
     */
    @PostMapping
    public String addFolder(@RequestBody Board board,
                            @SessionAttribute("loginMember") Member loginMember) {
        board.setMemberNo(loginMember.getMemberNo());
        return service.addBoardType(board) > 0 ? "success" : "fail";
    }

    /** 폴더 수정
     * @author 허배
     * @param board
     * @return
     */
    @PutMapping
    public String updateFolder(@RequestBody Board board) {
        return service.editBoardType(board) > 0 ? "success" : "fail";
    }

    /** 폴더 삭제
     * @author 허배
     * @param boardCode
     * @return
     */
    @DeleteMapping("/{boardCode}")
    public String deleteFolder(@PathVariable int boardCode) {
        return service.removeBoardType(boardCode) > 0 ? "success" : "fail";
    }

    /** 일촌공개 접근 가능 여부
     * @author 허배
     * @param ownerNo
     * @param loginMember
     * @return
     */
    @GetMapping("/access")
    public boolean checkAccess(@RequestParam("ownerNo") int ownerNo,
    						   @SessionAttribute("loginMember") Member loginMember){
        return service.canAccessFriendBoard(loginMember.getMemberNo(), ownerNo);
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



