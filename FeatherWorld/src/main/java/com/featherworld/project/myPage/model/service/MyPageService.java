package com.featherworld.project.myPage.model.service;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.featherworld.project.friend.model.service.IlchonService;


@Service
public class MyPageService {

    @Autowired
    private IlchonService ilchonService;

    public Map<String, Object> getIlchonMemberList(int memberNo, int cp) {
        return ilchonService.selectIlchonMemberList(memberNo, cp);
    }

    public int getIlchonCount(int memberNo) {
        Map<String, Object> ilchonData = ilchonService.selectIlchonMemberList(memberNo, 1); // 1 = 첫 페이지
        Integer count = (Integer) ilchonData.get("totalCount");
        return count != null ? count : 0;
    }


}

