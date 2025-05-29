package com.featherworld.project.profile.model.service;

import java.io.File;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.featherworld.project.common.utill.Utility;
import com.featherworld.project.profile.model.dto.Profile;
import com.featherworld.project.profile.model.mapper.ProfileMapper;

@Service
@Transactional(rollbackFor = Exception.class)
@PropertySource("classpath:/config.properties")
public class ProfileServiceImpl implements ProfileService {

	@Autowired
	private ProfileMapper mapper;

	@Value("${my.profile.web-path}")
	private String profileWebPath; /// myPage/profile/

	@Value("${my.profile.folder-path}")
	private String profileFolderPath; // C:/uploadFiles/profile/

	@Override
	public Profile selectProfile(int memberNo) {
		return mapper.selectProfile(memberNo);
	}

	@Override
	public int saveOrUpdateProfile(int loginMemberNo, MultipartFile uploadFile, String bio) throws Exception {

		int result = 0;

		// 변경명 저장
		String rename = null;

		// 업로드한 이미지가 있을 경우
		if (!uploadFile.isEmpty())
			rename = Utility.fileRename(uploadFile.getOriginalFilename());

		// 수정된 프로필 이미지 경로 + 회원 번호를 저장할 DTO 객체
		Profile newProfile = Profile.builder().memberNo(loginMemberNo).imgPath(profileWebPath)
				.imgOriginalName(uploadFile.getOriginalFilename()).imgRename(rename).profileContent(bio).build();

		Profile findProfileData = selectProfile(loginMemberNo);
		
		if (findProfileData != null) {
			result = mapper.updateProfile(newProfile);
		} else {
			result = mapper.insertProfile(newProfile);
		}

		if (result > 0) {
			// 프로필 이미지를 없애는 update를 한 경우를 제외
			// -> 업로드한 이미지가 있을 경우
			if (!uploadFile.isEmpty()) {
				// 파일을 서버에 저장
				uploadFile.transferTo(new File(profileFolderPath + rename));
				// C:/uploadFiles/profile/변경한 이름

			}
		}

		return result;
	}

	/**
	 *  회원 탈퇴
	 */
	@Autowired
	private PasswordEncoder bcrypt;

	@Override
	public int secession(String memberPw, int memberNo) {
	    String originPw = mapper.selectEncodedPw(memberNo); // XML id와 동일한 메서드명 사용

	    if (!bcrypt.matches(memberPw, originPw)) {
	        return 0;
	    }

	    return mapper.deleteMember(memberNo); // XML id와 동일한 메서드명 사용
	}




}
