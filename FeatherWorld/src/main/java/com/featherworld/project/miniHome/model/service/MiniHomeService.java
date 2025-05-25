package com.featherworld.project.miniHome.model.service;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.featherworld.project.friend.model.dto.Ilchon;
import com.featherworld.project.friend.model.dto.IlchonComment;
import com.featherworld.project.member.model.dto.Member;
import com.featherworld.project.miniHome.model.dto.MiniHomeRecentBoard; // ★ 추가된 import

public interface MiniHomeService {

    /** 사진 업로드
     * @param uploadFile
     * @return
     */
    String fileUpload1(MultipartFile uploadFile) throws Exception;

    /** 사진 db 저장
     * @param uploaFile
     * @param memberNo
     * @return
     */
    int fileUpload2(MultipartFile uploaFile, int memberNo) throws Exception;

    String fileUpload(MultipartFile uploadFile) throws Exception;

    /** 미니홈주인 찾아서 Member타입 반환
     * @param memberNo
     * @return
     * @author 영민
     */
    Member findmember(int memberNo);

    /** 일촌인지 아닌지확인
     * @param friend
     * @return
     */
    int findilchon(Ilchon friend);

    /** 일촌평 리스트 가져오기 */
    List<IlchonComment> getIlchonComments(int memberNo);

    /** 최근 게시글 리스트 가져오기 */
    List<MiniHomeRecentBoard> getRecentBoards(int memberNo);
}
