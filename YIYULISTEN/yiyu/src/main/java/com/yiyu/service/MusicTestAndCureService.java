package com.yiyu.service;

public interface MusicTestAndCureService {
    //向数据库中插入问卷信息
    void addQuestionnaire(String userId,  String date, String question, int score);

    //用户进行听力测试，向数据库中插入听力测试记录
    void addTest(String userId, String rightScore, String leftScore, String date, int allScore);

    void addFeedback(String userId, String feedback, String time, int feedbackType);

    //MusicInfo getMusicType(String userId);   接口已废除
}
