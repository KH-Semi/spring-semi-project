package com.featherworld.project.miniHome.model.service;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.featherworld.project.member.model.dto.Member;

@Service
public interface MiniHomeService {

	Member selectMemberByNo(int memberNo);

//	/** 사진 업로드
//	 * @param uploadFile
//	 * @return
//	 */
//	String fileUpload1(MultipartFile uploadFile) throws Exception;
//
//	/** 사진 db 저장
//	 * @param uploaFile
//	 * @param memberNo
//	 * @return
//	 */
//	int fileUpload2(MultipartFile uploaFile, int memberNo) throws Exception;

	
}
