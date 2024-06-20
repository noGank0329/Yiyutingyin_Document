package com.yiyu.service;

import com.yiyu.pojo.GetAllMusic.GetAllMusic;
import com.yiyu.pojo.GetCollection.GetCollection;
import com.yiyu.pojo.GetFeedback.GetFeedback;
import com.yiyu.pojo.GetQuestionnaireHistory.GetQuestionnaireHistory;
import com.yiyu.pojo.GetReplyFeedback.GetReplyFeedback;
import com.yiyu.pojo.GetTestHistory.GetTestHistory;

public interface UserHistoryService {
    GetQuestionnaireHistory[] questionnaireHistory(String userId);

    GetTestHistory[] testHistory(String userId);

    GetCollection[] collection(String userId);

    GetFeedback[] feedback(String userId);

    GetAllMusic[] music();

    GetReplyFeedback[] reply();
}
