package com.featherworld.project.miniHome.model.service;

import org.springframework.stereotype.Service;

@Service
public interface VisitService {
    int getTodayTotalCount(int memberNo);
}