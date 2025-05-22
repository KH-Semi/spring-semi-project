package com.featherworld.project.miniHome.model.service;

import java.io.File;

import org.eclipse.angus.mail.imap.Utility;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.featherworld.project.miniHome.model.dto.UploadFile;

@Service
@Transactional(rollbackFor = Exception.class)
@PropertySource("classpath:/config.properties")
public class MiniHomeServiceImpl implements MiniHomeService{


// 파일 업로드 테스트 1
	public String fileUpload(MultipartFile uploadFile) throws Exception {
		
		return null;
	}

	@Override
	public String fileUpload1(MultipartFile uploadFile) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int fileUpload2(MultipartFile uploaFile, int memberNo) throws Exception {
		// TODO Auto-generated method stub
		return 0;
	}
}