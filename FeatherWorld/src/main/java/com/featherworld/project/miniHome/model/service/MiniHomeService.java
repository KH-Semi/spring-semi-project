package com.featherworld.project.miniHome.model.service;

import org.springframework.web.multipart.MultipartFile;

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

}
