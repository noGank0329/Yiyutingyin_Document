package com.yiyu.controller;

import com.yiyu.pojo.GetAllMusic.GetAllMusic;
import com.yiyu.pojo.GetAllMusic.GetAllMusicResponse;
import com.yiyu.pojo.GetCollection.GetCollection;
import com.yiyu.pojo.GetCollection.GetCollectionResponse;
import com.yiyu.pojo.GetFeedback.GetFeedback;
import com.yiyu.pojo.GetFeedback.GetFeedbackResponse;
import com.yiyu.pojo.GetQuestionnaireHistory.GetQuestionnaireHistory;
import com.yiyu.pojo.GetQuestionnaireHistory.GetQuestionnaireHistoryResponse;
import com.yiyu.pojo.GetReplyFeedback.GetReplyFeedback;
import com.yiyu.pojo.GetReplyFeedback.GetReplyFeedbackResponse;
import com.yiyu.pojo.GetTestHistory.GetTestHistory;
import com.yiyu.pojo.GetTestHistory.GetTestHistoryResponse;
import com.yiyu.pojo.Result;
import com.yiyu.service.UserHistoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/UserHistory")
public class UserHistoryController {
    @Autowired
    private UserHistoryService userHistoryService;

    @GetMapping("/QuestionnaireHistory")      //查看问卷历史记录
    public Result questionnaireHistory(String user_id){
        GetQuestionnaireHistory[] getQuestionnaireHistory;
        getQuestionnaireHistory=userHistoryService.questionnaireHistory(user_id);
        return Result.success(new GetQuestionnaireHistoryResponse(List.of(getQuestionnaireHistory)));
    }

    @GetMapping("/TestHistory")        //查看测试历史
    public Result testHistory(String user_id){
        GetTestHistory[] g;
        g=userHistoryService.testHistory(user_id);
        return Result.success(new GetTestHistoryResponse(List.of(g)));
    }


    @GetMapping("/Collection")     //查看收藏列表
    public Result collection(String user_id){
        GetCollection[] c;
        c=userHistoryService.collection(user_id);
        return Result.success(new GetCollectionResponse(List.of(c)));
    }


    @GetMapping("/FeedbackList")     //查看反馈列表，根据返回的result是否为NULL来判断反馈是否被处理
    public Result feedback(String user_id){
        GetFeedback[] g;
        g=userHistoryService.feedback(user_id);
        return Result.success(new GetFeedbackResponse(List.of(g)));
    }

    @GetMapping("/AllMusic")    //查看所有音乐
    public Result music(){
        GetAllMusic[] g;
        g=userHistoryService.music();
        return Result.success(new GetAllMusicResponse(List.of(g)));
    }

    @GetMapping("/FeedbackReplyList")    //管理员获取反馈结果列表
    public Result reply(){
        GetReplyFeedback[] g;
        g=userHistoryService.reply();
        return Result.success(new GetReplyFeedbackResponse(List.of(g)));
    }
}
