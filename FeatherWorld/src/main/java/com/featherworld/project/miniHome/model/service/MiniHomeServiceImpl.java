package com.featherworld.project.miniHome.model.service;

import java.io.File;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.featherworld.project.member.model.dto.Member;
import com.featherworld.project.miniHome.model.mapper.MiniHomeMapper;

@Service
public class MiniHomeServiceImpl implements MiniHomeService {

    @Autowired
    private MiniHomeMapper mapper;

    // 기본 이미지 저장 경로 (개발 환경에 따라 변경)
    private final String FILE_PATH = "C:/featherworld/upload/";

    @Override
    public Member selectMemberByNo(int memberNo) {
        return mapper.selectMemberByNo(memberNo);
    }

    @Override
    public int fileUpload3(List<MultipartFile> aaaList, List<MultipartFile> bbbList, int memberNo) throws Exception {
        int uploadCount = 0;

        // 업로드할 모든 파일을 하나의 리스트에 담기
        for (MultipartFile file : aaaList) {
            if (!file.isEmpty()) {
                saveFile(file, memberNo, "aaa");
                uploadCount++;
            }
        }

        for (MultipartFile file : bbbList) {
            if (!file.isEmpty()) {
                saveFile(file, memberNo, "bbb");
                uploadCount++;
            }
        }

        return uploadCount;
    }

    // 실제 파일 저장 처리
    private void saveFile(MultipartFile file, int memberNo, String type) throws Exception {
        String originalName = file.getOriginalFilename();
        String extension = originalName.substring(originalName.lastIndexOf("."));
        String renamed = UUID.randomUUID().toString() + extension;

        File uploadDir = new File(FILE_PATH + memberNo + "/");
        if (!uploadDir.exists()) uploadDir.mkdirs();

        File dest = new File(uploadDir, renamed);
        file.transferTo(dest);

        // DB에 파일 정보 저장
        mapper.insertImage(memberNo, originalName, renamed, type);
    }
}
