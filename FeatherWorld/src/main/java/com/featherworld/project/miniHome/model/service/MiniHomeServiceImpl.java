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
public class MiniHomeServiceImpl implements MiniService{



// 파일 업로드 테스트 1
	public String fileUpload(MultipartFile uploadFile) throws Exception {
		
		
		
		// 업로드한 파일이 없을 경우
		if( uploadFile.isEmpty() ) {
			return null;
		}
		
		
		uploadFile.transferTo(new File("C:/uploadFiles/test/" + uploadFile.getOriginalFilename()));
		
	
		
		return "/myPage/file/" + uploadFile.getOriginalFilename();
	}


	// 파일 업로드 테스트 2 (+DB)
	@Override
	public int fileUpload2(MultipartFile uploadFile, int memberNo) throws Exception {
		
		// 업로드된 파일이 없을때
		if(uploadFile.isEmpty()) {
			
			String folderPath = "C:/uploadFiles/test/";
			
			
			// 클라이언트가 저장된 폴더 접근 주소
			
//			String webPath = "/{memberNo:[0-9]+}/minihome";
			
//			// DB에 데이터 DTO로 묶기
//			String fileRename = Utility.fileRename(uploadFile.getOriginalFilename());
//			
//			// Builder 패턴을 이용해서 UploadFile 객체 생성
//			UploadFile uf = UploadFile.builder()
//					.memberNo(memberNo)
//					.filePath(webPath)
//					.fileRename(fileRename)
//					.build();
//			
//			// DTO 객체를 DB에 전달하기(INSERT 하기)
//			int result = mapper.insertUploadFile(uf);
//			
//			// 4. 삽입 성공시 저장
//			if(result == 0) return 0;
//			
//			uploadFile.transferTo(new File(folderPath+fileRename));
//		
//			return "redirect:/minihome/minihome";
		
	}


	@Override
	public String fileUpload1(MultipartFile uploadFile) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	


	}