package com.yiyu.service.impl;

import com.yiyu.mapper.UserHistoryMapper;
import com.yiyu.pojo.GetAllMusic.GetAllMusic;
import com.yiyu.pojo.GetCollection.GetCollection;
import com.yiyu.pojo.GetFeedback.GetFeedback;
import com.yiyu.pojo.GetQuestionnaireHistory.GetQuestionnaireHistory;
import com.yiyu.pojo.GetReplyFeedback.GetReplyFeedback;
import com.yiyu.pojo.GetTestHistory.GetTestHistory;
import com.yiyu.service.UserHistoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserHistoryServiceImpl implements UserHistoryService {
    @Autowired
    private UserHistoryMapper userHistoryMapper;
    @Override
    public GetQuestionnaireHistory[] questionnaireHistory(String userId) {
        GetQuestionnaireHistory[] getQuestionnaireHistories;
        getQuestionnaireHistories=userHistoryMapper.QuestionnaireHistory(userId);
        return getQuestionnaireHistories;
    }

    @Override
    public GetTestHistory[] testHistory(String userId) {
        GetTestHistory[] g;
        g=userHistoryMapper.testHistory(userId);
        return g;
    }

    @Override
    public GetCollection[] collection(String userId) {
        GetCollection[] c;
        c=userHistoryMapper.collection(userId);
        return c;
    }

    @Override
    public GetFeedback[] feedback(String userId) {
        GetFeedback[] g;
        g=userHistoryMapper.feedback(userId);
        return g;
    }

    @Override
    public GetAllMusic[] music() {
        GetAllMusic[] g;
        g=userHistoryMapper.music();
        return g;
    }

    @Override
    public GetReplyFeedback[] reply() {
        GetReplyFeedback[] g;
        g=userHistoryMapper.reply();
        return g;
    }
}
