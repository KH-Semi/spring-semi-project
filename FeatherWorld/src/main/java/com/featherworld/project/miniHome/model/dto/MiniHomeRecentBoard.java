package com.featherworld.project.miniHome.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MiniHomeRecentBoard {
    private int boardNo;
    private String title;
    private String thumbnailImg;  // 이미지 경로 + 이름 조합
}
