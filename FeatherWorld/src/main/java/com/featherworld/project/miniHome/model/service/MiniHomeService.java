package com.featherworld.project.miniHome.model.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.featherworld.project.member.model.dto.Member;
import com.featherworld.project.miniHome.model.dto.UploadFile;

public interface MiniHomeService {

	

	/** minihome 업로드 서비스
	 * @param aaaList
	 * @param bbbList
	 * @param memberNo
	 * @return
	 * @throws Exception
	 */
	int fileUpload3(List<MultipartFile> aaaList, 
					List<MultipartFile> bbbList, 
					int memberNo) throws Exception;
	
}