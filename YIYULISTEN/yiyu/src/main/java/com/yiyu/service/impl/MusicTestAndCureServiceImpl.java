package com.yiyu.service.impl;

import com.yiyu.mapper.MusicTestAndCureMapper;
import com.yiyu.service.MusicTestAndCureService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MusicTestAndCureServiceImpl implements MusicTestAndCureService {
    @Autowired
    private MusicTestAndCureMapper musicTestAndCureMapper;
    @Override
    public void addQuestionnaire(String userId, String date, String question, int score) {
        musicTestAndCureMapper.addQuestionnaire(userId,date,question,score);
    }

    @Override
    public void addTest(String userId, String rightScore, String leftScore, String date, int allScore) {
        musicTestAndCureMapper.addTest(userId,rightScore,leftScore,date,allScore);
    }

    @Override
    public void addFeedback(String userId, String feedback, String time, int feedbackType) {
        musicTestAndCureMapper.addFeedback(userId,feedback,time,feedbackType);
    }

    /*@Override     接口已废除
    public MusicInfo getMusicType(String userId) {
        MusicInfo m=musicTestAndCureMapper.getMusicType(userId);
        return m;
    }*/
}
